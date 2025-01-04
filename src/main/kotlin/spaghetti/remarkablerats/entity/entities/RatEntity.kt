// TODO: Resolve deprecations
@file:Suppress("DEPRECATION")

package spaghetti.remarkablerats.entity.entities

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.advancement.criterion.Criteria
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.NbtComponent
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
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.*
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtList
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.DyeColor
import net.minecraft.util.Hand
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.LocalDifficulty
import net.minecraft.world.ServerWorldAccess
import net.minecraft.world.World
import spaghetti.remarkablerats.entity.RatEntities
import spaghetti.remarkablerats.item.RatItems
import spaghetti.remarkablerats.network.EntityIdPayload
import spaghetti.remarkablerats.screen.RatEntityScreenHandler
import spaghetti.remarkablerats.sound.RatSounds
import spaghetti.remarkablerats.tags.RatTags.Items.rat_consumable_items

class RatEntity(entityType: EntityType<out TameableEntity>, world: World?) : TameableEntity(entityType, world),
        Bucketable, Inventory, ExtendedScreenHandlerFactory<EntityIdPayload> {

    /*** Variables ***/

    val idleAnimationState: AnimationState = AnimationState()
    private var idleAnimationCooldown = 0
    private var inventory: DefaultedList<ItemStack> = DefaultedList.ofSize(27, ItemStack.EMPTY)
    private var wasSitting: Boolean = false;

    /*** Companions (static things) ***/

    companion object {
        private val variant: TrackedData<Int> =
                DataTracker.registerData(RatEntity::class.java, TrackedDataHandlerRegistry.INTEGER)
        private val outfit_color: TrackedData<Int> =
                DataTracker.registerData(RatEntity::class.java, TrackedDataHandlerRegistry.INTEGER)
        private val from_bucket: TrackedData<Boolean> =
                DataTracker.registerData(RatEntity::class.java, TrackedDataHandlerRegistry.BOOLEAN)


        fun createAttributes(): DefaultAttributeContainer.Builder {
            return createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                    .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0)
        }
    }

    /*** Initializations ***/

    override fun initialize(world: ServerWorldAccess?, difficulty: LocalDifficulty?, spawnReason: SpawnReason?,
            entityData: EntityData?): EntityData? {
        if (spawnReason == SpawnReason.BUCKET) return entityData
        setVariant(RatVariant.getRandom(this.random))
        return super.initialize(world, difficulty, spawnReason, entityData)
    }

    override fun initGoals() {
        goalSelector.add(0, SwimGoal(this))
        goalSelector.add(0, PowderSnowJumpGoal(this, this.world))
        goalSelector.add(1, EscapeDangerGoal(this, 1.0))
        goalSelector.add(2, SitGoal(this))
        goalSelector.add(4, PounceAtTargetGoal(this, 0.4f))
        goalSelector.add(5, MeleeAttackGoal(this, 1.0, true))
        goalSelector.add(6, FollowOwnerGoal(this, 1.0, 10.0f, 2.0f))
        goalSelector.add(7, AnimalMateGoal(this, 1.0))
        goalSelector.add(8, WanderAroundFarGoal(this, 1.0))
        goalSelector.add(10, LookAtEntityGoal(this, PlayerEntity::class.java, 8.0f))
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
        super.tick()
        if (world.isClient()) {
            setupAnimationStates()
        }
    }

    // TODO: Add logic for inheriting parent's color
    override fun createChild(world: ServerWorld?, entity: PassiveEntity?): PassiveEntity? {
        // "this" is the parent spawning the child, and "entity" is the other parent
        val child = RatEntities.rat.create(world)
        child?.setVariant(RatVariant.getRandom(this.random))
        return child
    }

    override fun isBreedingItem(stack: ItemStack): Boolean {
        return stack.isIn(rat_consumable_items)
    }

    override fun getAmbientSound(): SoundEvent {
        return RatSounds.rat_squeak_event
    }

    override fun interactMob(player: PlayerEntity, hand: Hand): ActionResult {
        val stack = player.getStackInHand(hand)
        val item: Item = stack.item
        return if (item === Items.BUNDLE) tryBundle(stack, player, hand)
        else if (this.isTamed) tamedInteraction(stack, player, item, hand)
        else if (stack.contains(DataComponentTypes.FOOD)) tryTaming(player, hand, item)
        else super.interactMob(player, hand)
    }

    /*** NBT save/load ***/

    override fun initDataTracker(builder: DataTracker.Builder) {
        super.initDataTracker(builder)
        builder.add(variant, 0)
        builder.add(outfit_color, DyeColor.RED.id)
        builder.add(from_bucket, false)
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
        nbt.putInt("Variant", getTypeVariant())
        nbt.putInt("OutfitColor", getOutfitColor().id)
        nbt.putBoolean("FromBucket", isFromBucket)
        nbt.putInt("Age", this.getBreedingAge())

        //inventory
        val nbtList = NbtList()
        for (i in 1..<inventory.size) {
            val itemStack: ItemStack = inventory[i]
            if (!itemStack.isEmpty) {
                val nbtCompound = NbtCompound()
                nbtCompound.putByte("Slot", (i - 1).toByte())
                nbtList.add(itemStack.encode(this.registryManager, nbtCompound))
            }
        }
        nbt.put("Items", nbtList)
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
        this.dataTracker.set(variant, nbt.getInt("Variant"))
        if (nbt.contains("OutfitColor")) this.dataTracker.set(outfit_color, nbt.getInt("OutfitColor"))
        if (nbt.contains("Age")) this.setBreedingAge(nbt.getInt("Age"))

        // inventory
        val nbtList = nbt.getList("Items", NbtElement.COMPOUND_TYPE.toInt())
        for (i in nbtList.indices) {
            val nbtCompound = nbtList.getCompound(i)
            val j = nbtCompound.getByte("Slot").toInt() and 255
            if (j < inventory.size - 1) {
                inventory[j + 1] = ItemStack.fromNbt(this.registryManager, nbtCompound).orElse(ItemStack.EMPTY) as ItemStack
            }
        }
    }

    /*** Bundling ***/

    // Rat NBT -> Bundle NBT
    override fun copyDataToStack(stack: ItemStack) {
        Bucketable.copyDataToStack(this, stack)
        NbtComponent.set(DataComponentTypes.BUCKET_ENTITY_DATA, stack) { nbt: NbtCompound ->
            nbt.putInt("Variant", getTypeVariant())
            nbt.putInt("OutfitColor", getOutfitColor().id)
            nbt.putBoolean("FromBucket", isFromBucket)
            nbt.putInt("Age", this.getBreedingAge())
            if (ownerUuid != null) {
                nbt.putUuid("OwnerUuid", ownerUuid)
                nbt.putString("OwnerName", owner?.name?.string ?: "No owner name found")
            }
            Inventories.writeNbt(nbt, inventory, registryManager)
        }
    }

    // Bundle NBT -> Rat NBT
    override fun copyDataFromNbt(nbt: NbtCompound) {
        Bucketable.copyDataFromNbt(this, nbt)
        this.dataTracker.set(variant, nbt.getInt("Variant"))
        println("readCustomDataFromNbt was called")
        if (nbt.contains("OutfitColor")) this.dataTracker.set(outfit_color, nbt.getInt("OutfitColor"))
        if (nbt.contains("Age")) {
            this.setBreedingAge(nbt.getInt("Age"))
        }
        if (nbt.contains("OwnerUuid") && (null != world.getPlayerByUuid(nbt.getUuid("OwnerUuid")))) {
            setOwner(world.getPlayerByUuid(nbt.getUuid("OwnerUuid")))
        }
        Inventories.readNbt(nbt, inventory, registryManager)
    }

    private fun tryBundle(stack: ItemStack, player: PlayerEntity, hand: Hand): ActionResult {
        if (stack.item !== Items.BUNDLE || !this.isAlive) return ActionResult.FAIL

        this.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 1.0f, 1.0f)

        // TODO: check if as Bucketable is necessary
        val itemStack2 = (this as Bucketable).bucketItem
        (this as Bucketable).copyDataToStack(itemStack2)
        val itemStack3 = ItemUsage.exchangeStack(stack, player, itemStack2, false)
        player.setStackInHand(hand, itemStack3)
        val world = this.world
        if (!world.isClient) {
            Criteria.FILLED_BUCKET.trigger(player as ServerPlayerEntity, itemStack2)
        }
        this.discard()
        return ActionResult.success(world.isClient)
    }

    override fun isFromBucket(): Boolean = dataTracker.get(from_bucket)

    override fun setFromBucket(isFromBucket: Boolean) {
        dataTracker.set(from_bucket, isFromBucket)
    }

    override fun getBucketItem(): ItemStack {
        return RatItems.bundle_of_rats.defaultStack
    }

    override fun getBucketFillSound(): SoundEvent {
        return SoundEvents.ITEM_BUNDLE_INSERT
    }

    /*** VARIANT ***/

    // TODO: double check if the "and 255" is necessary, as the old project didn't use it
    fun getVariant(): RatVariant = RatVariant.byId(this.getTypeVariant() and 255)

    private fun getTypeVariant(): Int = this.dataTracker.get(variant)

    private fun setVariant(newVariant: RatVariant) {
        this.dataTracker.set(variant, newVariant.id and 255)
    }

    /*** Taming ***/

    private fun tryTaming(player: PlayerEntity, hand: Hand, item: Item): ActionResult {
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

    /*** Tamed behavior ***/

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

        // unless the interacting player is the owner, no more interactions are possible
        if (!this.isOwner(player)) {
            return ActionResult.FAIL
        }

        // dying
        if (item is DyeItem) {
            val dyeColor = item.color
            if (dyeColor == this.getOutfitColor()) return super.interactMob(player, hand)
            this.setOutfitColor(dyeColor)
            if (player.abilities.creativeMode) return ActionResult.SUCCESS
            stack.decrement(1)
            return ActionResult.SUCCESS
        }

        // TODO: Clean up
        // breeding
        if ((super.interactMob(player, hand).also { actionResult = it }).isAccepted) return actionResult


        // TODO: decide if opening inventory should always take priority and all other actions should require sneaking
        // sitting or opening inventory
        if (player.isSneaking) {
            this.isSitting = !this.isSitting
            this.jumping = false
            navigation.stop()
            this.target = null
            return ActionResult.SUCCESS
        } else {
            player.openHandledScreen(this)
            return ActionResult.SUCCESS
        }
    }

    /*** Outfits ***/

    // TODO: Implement outfits and dying
    private fun getOutfitColor(): DyeColor = DyeColor.byId(dataTracker.get(outfit_color))

    private fun setOutfitColor(color: DyeColor) {
        dataTracker.set(outfit_color, color.id)
    }

    /*** Inventory behavior ***/

    override fun clear() {
        inventory.clear()
    }

    override fun size(): Int {
        return inventory.size
    }

    override fun isEmpty(): Boolean {
        inventory.forEach { itemStack ->
            if (!itemStack.isEmpty) {
                return false
            }
        }
        return true
    }

    override fun getStack(slot: Int): ItemStack {
        return inventory[slot]
    }

    override fun removeStack(slot: Int, amount: Int): ItemStack {
        return Inventories.splitStack(inventory, slot, amount)
    }

    override fun removeStack(slot: Int): ItemStack {
        val itemStack: ItemStack = inventory[slot]
        if (itemStack.isEmpty) {
            return ItemStack.EMPTY
        } else {
            inventory[slot] = ItemStack.EMPTY
            return itemStack
        }
    }

    override fun setStack(slot: Int, stack: ItemStack) {
        inventory[slot] = stack
        stack.capCount(this.getMaxCount(stack))
    }

    override fun markDirty() {

    }

    override fun canPlayerUse(player: PlayerEntity?): Boolean {
        return true
    }

    /*** Inventory Display */

    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity): ScreenHandler {
        return RatEntityScreenHandler(syncId, playerInventory, this);
    }

    override fun getScreenOpeningData(p0: ServerPlayerEntity?): EntityIdPayload {
        return EntityIdPayload(this.id)
    }

    fun onScreenOpened() {
        wasSitting = isSitting;
        isSitting = true
    }
    fun onScreenClosed() {
        isSitting = wasSitting
    }
}