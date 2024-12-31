package spaghetti.remarkablerats.entity.entities

import net.minecraft.entity.AnimationState
import net.minecraft.entity.Bucketable
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.passive.TameableEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.world.World
import spaghetti.remarkablerats.entity.ModEntities
import spaghetti.remarkablerats.sound.ModSounds
import spaghetti.remarkablerats.tags.ModTags.Items.rat_consumable_items

class RatEntity(entityType: EntityType<out TameableEntity>, world: World?)
    : TameableEntity(entityType, world), Bucketable {

    val idleAnimationState: AnimationState = AnimationState()
    private var idleAnimationCooldown = 0

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
        return ModEntities.rat.create(world);
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
        return ModSounds.rat_squeak_event
    }

}