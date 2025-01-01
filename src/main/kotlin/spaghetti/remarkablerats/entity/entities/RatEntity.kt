package spaghetti.remarkablerats.entity.entities

import net.minecraft.advancement.criterion.Criteria
import net.minecraft.component.DataComponentTypes
import net.minecraft.entity.*
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.passive.TameableEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.*
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.DyeColor
import net.minecraft.util.Hand
import net.minecraft.world.LocalDifficulty
import net.minecraft.world.ServerWorldAccess
import net.minecraft.world.World
import spaghetti.remarkablerats.entity.RatEntities
import spaghetti.remarkablerats.sound.RatSounds
import spaghetti.remarkablerats.tags.RatTags.Items.rat_consumable_items


class RatEntity(entityType: EntityType<out TameableEntity>, world: World?)
    : TameableEntity(entityType, world), Bucketable {
    val idleAnimationState: AnimationState = AnimationState();
    private var idleAnimationCooldown = 0;

    override fun initialize(
        world: ServerWorldAccess?,
        difficulty: LocalDifficulty?,
        spawnReason: SpawnReason?,
        entityData: EntityData?
    ): EntityData? {
        if (spawnReason == SpawnReason.BUCKET) return entityData;
        setVariant(RatVariant.getRandom(this.random));
        return super.initialize(world, difficulty, spawnReason, entityData)
    }

    override fun initGoals() {
        goalSelector.add(0, SwimGoal(this))
        goalSelector.add(1, EscapeDangerGoal(this, 1.0))
        goalSelector.add(2, SitGoal(this))
        goalSelector.add(4, PounceAtTargetGoal(this, 0.4f))
        goalSelector.add(5, MeleeAttackGoal(this, 1.0, true))
        goalSelector.add(6, FollowOwnerGoal(this, 1.0, 10.0f, 2.0f))
        goalSelector.add(7, AnimalMateGoal(this, 1.0))
        goalSelector.add(8, WanderAroundFarGoal(this, 1.0))
        goalSelector.add(
            10, LookAtEntityGoal(
                this,
                PlayerEntity::class.java, 8.0f
            )
        )
        goalSelector.add(10, LookAroundGoal(this))

        targetSelector.add(1, TrackOwnerAttackerGoal(this))
        targetSelector.add(2, AttackWithOwnerGoal(this))
        targetSelector.add(3, RevengeGoal(this).setGroupRevenge())
    }

    private fun setupAnimationStates() {
        if (this.idleAnimationCooldown <= 0) {
            this.idleAnimationCooldown = random.nextInt(40) + 80
            idleAnimationState.start(this.age)
        } else {
            idleAnimationCooldown--
        }
    }

    override fun tick() {
        super.tick();
        if (world.isClient()) {
            setupAnimationStates()
        }
    }

    // TODO: Add logic for inheriting parent's color
    override fun createChild(world: ServerWorld?, entity: PassiveEntity?): PassiveEntity? {
        // "this" is the parent spawning the child, and "entity" is the other parent
        val child = RatEntities.rat.create(world);
        child?.setVariant(RatVariant.getRandom(this.random));
        return child;
    }

    override fun isBreedingItem(stack: ItemStack): Boolean {
        return stack.isIn(rat_consumable_items);
    }

    override fun getAmbientSound(): SoundEvent {
        return RatSounds.rat_squeak_event
    }

    override fun interactMob(player: PlayerEntity, hand: Hand): ActionResult {
        val stack = player.getStackInHand(hand)
        val item: Item = stack.item

        // bundling
        if (item === Items.BUNDLE) return tryBundle(stack, player, hand);

        if (this.isTamed) return tamedInteraction(stack, player, item, hand);

        if (stack.contains(DataComponentTypes.FOOD)) { // taming
            if (!player.abilities.creativeMode) {
                player.getStackInHand(hand).decrement(1)
            }
            val saturation = item.components.get(DataComponentTypes.FOOD)!!.saturation
            if (random.nextInt(1.coerceAtLeast(6 - saturation.toInt())) == 0) {
                this.setOwner(player)
                navigation.stop()
                this.isSitting = true
                this.target = null
                world.sendEntityStatus(this, 7.toByte())
            } else {
                for (i in 0..6) {
                    world.sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES)
                }
            }
            return ActionResult.SUCCESS
        }
        return super.interactMob(player, hand)
    }

    /*** NBT save/load
     */

    override fun initDataTracker(builder: DataTracker.Builder) {
        super.initDataTracker(builder);
        builder.add(variant, 0);
        builder.add(outfit_color, 0);
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
        nbt.putInt("Variant", this.getTypeVariant());
        nbt.putInt("OutfitColor", this.getOutfitColor().id)
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(variant, nbt.getInt("Variant"));
        println("readCustomDataFromNbt was called")
        if (nbt.contains("OutfitColor"))
            this.dataTracker.set(outfit_color, nbt.getInt("OutfitColor"));
    }

    /*** Tamed behavior
     */

    private fun tamedInteraction(stack: ItemStack, player: PlayerEntity, item: Item, hand: Hand): ActionResult {
        val actionResult: ActionResult
        // healing
        if (this.isBreedingItem(stack) && this.health < this.maxHealth) {
            if (!player.abilities.creativeMode) {
                stack.decrement(1)
            }
            if (stack.contains(DataComponentTypes.FOOD)) {
                stack.get(DataComponentTypes.FOOD)?.let { this.heal(it.saturation) }
            }
            return ActionResult.SUCCESS
        }
        // dying
        if (item is DyeItem) {
            if (this.isOwner(player)) {
                val dyeColor = item.color
                if (dyeColor == this.getOutfitColor()) return super.interactMob(player, hand)
                this.setOutfitColor(dyeColor)
                if (player.abilities.creativeMode) return ActionResult.SUCCESS
                stack.decrement(1)
                return ActionResult.SUCCESS
            }
        }

        if ((super.interactMob(player, hand)
                .also { actionResult = it }).isAccepted && !this.isBaby || !this.isOwner(player)
        ) return actionResult
        this.isSitting = !this.isSitting
        this.jumping = false
        navigation.stop()
        this.target = null
        return ActionResult.SUCCESS
    }

    /*** Outfits
     */

    // TODO: Implement outfits and dying
    private fun getOutfitColor(): DyeColor = DyeColor.byId(dataTracker.get(outfit_color))

    private fun setOutfitColor(color: DyeColor) {
        dataTracker.set(outfit_color, color.id)
    }

    /***
     * Bundling
     */

    private fun tryBundle(stack: ItemStack, player: PlayerEntity, hand: Hand): ActionResult {
        if (stack.item !== Items.BUNDLE || !this.isAlive) return ActionResult.FAIL;

        this.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 1.0f, 1.0f);

        // TODO: check if as Bucketable is necessary
        val itemStack2 = (this as Bucketable).bucketItem;
        (this as Bucketable).copyDataToStack(itemStack2);
        val itemStack3 = ItemUsage.exchangeStack(stack, player, itemStack2, false);
        player.setStackInHand(hand, itemStack3)
        val world = this.world
        if (!world.isClient) {
            Criteria.FILLED_BUCKET.trigger(player as ServerPlayerEntity, itemStack2)
        }
        this.discard()
        return ActionResult.success(world.isClient)
    }

    override fun isFromBucket(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setFromBucket(fromBucket: Boolean) {
        TODO("Not yet implemented")
    }

    // part of bucketing, not regular NBT behavior
    override fun copyDataToStack(stack: ItemStack?) {
        TODO("Not yet implemented")
    }

    override fun copyDataFromNbt(nbt: NbtCompound?) {
        TODO("Not yet implemented")
    }

    override fun getBucketItem(): ItemStack {
        TODO("Not yet implemented")
    }

    override fun getBucketFillSound(): SoundEvent {
        return SoundEvents.ITEM_BUNDLE_INSERT
    }

    /*** VARIANT */

    // TODO: double check if the "and 255" is necessary, as the old project didn't use it
    fun getVariant() : RatVariant = RatVariant.byId(this.getTypeVariant() and 255);

    private fun getTypeVariant() : Int = this.dataTracker.get(variant);

    private fun setVariant(newVariant: RatVariant) {
        this.dataTracker.set(variant, newVariant.id and 255);
    }

    /*** Companions (static things)
     */

    companion object {
        private val variant: TrackedData<Int> =
            DataTracker.registerData(RatEntity::class.java, TrackedDataHandlerRegistry.INTEGER);
        private val outfit_color: TrackedData<Int> =
            DataTracker.registerData(RatEntity::class.java, TrackedDataHandlerRegistry.INTEGER);

        fun createAttributes(): DefaultAttributeContainer.Builder {
            return createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0)
        }
    }
}