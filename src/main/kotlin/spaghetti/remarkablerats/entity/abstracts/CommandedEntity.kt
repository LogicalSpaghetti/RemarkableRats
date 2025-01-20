package spaghetti.remarkablerats.entity.abstracts

import net.minecraft.entity.EntityType
import net.minecraft.entity.passive.TameableEntity
import net.minecraft.registry.tag.FluidTags
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.event.GameEvent
import spaghetti.remarkablerats.logger

abstract class CommandedEntity protected constructor(entityType: EntityType<out TameableEntity>, world: World) :
        TameableEntity(entityType, world) {
    var targetTypeList: List<String> = listOf()
    var targetedDataList: List<Int> = listOf()

    abstract fun getInstructionStage(): Int
    abstract fun incrementInstructionStage()

    fun getCurrentInstructionType(): String {
        if (targetTypeList.isEmpty()) return ""
        return targetTypeList[getInstructionStage() % targetTypeList.size]
    }

    fun getCurrentInstructionData(): Int {
        if (targetedDataList.isEmpty()) return 0
        return targetedDataList[getInstructionStage() % targetedDataList.size]
    }

    fun reachedTarget() {
        incrementInstructionStage()
        logger.info("reached target")
        logger.info("getInstructionStage() = ${getInstructionStage()}")
    }

    fun teleportTo(x: Double, y: Double, z: Double): Boolean {
        val mutable = BlockPos.Mutable(x, y, z)

        while (mutable.y > world.bottomY && !world.getBlockState(mutable).blocksMovement()) {
            mutable.move(Direction.DOWN)
        }

        val blockState = world.getBlockState(mutable)
        val bl = blockState.blocksMovement()
        val bl2 = blockState.fluidState.isIn(FluidTags.WATER)
        if (bl && !bl2) {
            val vec3d = this.pos
            val bl3 = this.teleport(x, y, z, true)
            if (bl3) {
                world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(this))
                if (!this.isSilent) {
                    world.playSound(null, this.prevX, this.prevY, this.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                            this.soundCategory, 1.0f, 1.0f)
                    this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f)
                }
            }

            return bl3
        } else {
            return false
        }
    }
}
