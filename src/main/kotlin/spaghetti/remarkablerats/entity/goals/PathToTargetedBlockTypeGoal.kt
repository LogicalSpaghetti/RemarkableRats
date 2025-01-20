package spaghetti.remarkablerats.entity.goals

import net.minecraft.block.Block
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal
import net.minecraft.entity.mob.PathAwareEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.WorldView
import spaghetti.remarkablerats.entity.abstracts.CommandedEntity
import spaghetti.remarkablerats.entity.enums.RatActionType

class PathToTargetedBlockTypeGoal(val entity: CommandedEntity, speed: Double, range: Int): MoveToTargetPosGoal(entity, speed, range) {

    override fun isTargetPos(world: WorldView, pos: BlockPos): Boolean {
        return entity.getCurrentInstructionType() == RatActionType.MOVE_TO_BLOCKSTATE.type &&
               (world.isAir(pos.up())) &&
               world.getBlockState(pos).equals(Block.getStateFromRawId(entity.getCurrentInstructionData()))
    }

    override fun canStart(): Boolean {
        return this.entity.isTamed && !this.entity.isSitting && super.canStart()
    }

    override fun start() {
        super.start()
        val blockPos = this.getTargetPos().up()
        entity.teleportTo(blockPos.x + 0.5, blockPos.y.toDouble(), blockPos.z + 0.5)
        entity.reachedTarget()
//        this.entity.setInSittingPose(false)
        stop()
    }

    override fun stop() {
        super.stop()
        this.entity.setInSittingPose(false)
    }

    override fun tick() {
//        super.tick()
    }

    override fun getInterval(mob: PathAwareEntity?): Int = 0

    override fun getDesiredDistanceToTarget(): Double = 1.0
}