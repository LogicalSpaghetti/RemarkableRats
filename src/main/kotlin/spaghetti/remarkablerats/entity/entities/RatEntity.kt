package spaghetti.remarkablerats.entity.entities

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
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
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

    companion object {
        private val dataVariant: TrackedData<Int> =
            DataTracker.registerData(RatEntity::class.java, TrackedDataHandlerRegistry.INTEGER);
        fun createAttributes(): DefaultAttributeContainer.Builder {
            return createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0)
        }
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

    override fun createChild(world: ServerWorld?, entity: PassiveEntity?): PassiveEntity? {
        // "this" is the parent spawning the child, and "entity" is the other parent
        val child = RatEntities.rat.create(world);
        child?.setVariant(RatVariant.getRandom(this.random));
        return child;
    }

    override fun isBreedingItem(stack: ItemStack): Boolean {
        return stack.isIn(rat_consumable_items);
    }

    override fun isFromBucket(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setFromBucket(fromBucket: Boolean) {
        TODO("Not yet implemented")
    }

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

    override fun getAmbientSound(): SoundEvent {
        return RatSounds.rat_squeak_event
    }

    /* VARIANT */
    override fun initDataTracker(builder: DataTracker.Builder) {
        super.initDataTracker(builder);
        println(dataVariant)
        builder.add(dataVariant, 0);
    }

    // double check if the "and 255" is necessary, as the old project didn't use it
    fun getVariant() : RatVariant = RatVariant.byId(this.getTypeVariant() and 255);

    private fun getTypeVariant() : Int = this.dataTracker.get(dataVariant);

    private fun setVariant(newVariant: RatVariant) {
        this.dataTracker.set(dataVariant, newVariant.id and 255);
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
        nbt.putInt("Variant", this.getTypeVariant());
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(dataVariant, nbt.getInt("Variant"));
    }

    override fun initialize(
        world: ServerWorldAccess?,
        difficulty: LocalDifficulty?,
        spawnReason: SpawnReason?,
        entityData: EntityData?
    ): EntityData? {
        println("Initializing Rat")
        if (spawnReason == SpawnReason.BUCKET) return entityData;
        println("Initializing Non-bucketed Rat")
        setVariant(RatVariant.getRandom(this.random));
        println("Variant set")
        return super.initialize(world, difficulty, spawnReason, entityData)
    }
}