package spaghetti.remarkablerats.entity.goals

import net.minecraft.block.Block
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal
import net.minecraft.entity.mob.PathAwareEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.WorldView
import spaghetti.remarkablerats.entity.abstracts.CommandedEntity
import spaghetti.remarkablerats.entity.enums.RatActionType

// TODO: get the walking to consistently reach the desired block instead of standing next to it
class MoveToTargetedBlockTypeGoal(val entity: CommandedEntity, speed: Double, range: Int): MoveToTargetPosGoal(entity, speed, range) {

    override fun isTargetPos(world: WorldView, pos: BlockPos): Boolean {
        return entity.getCurrentInstructionType() == RatActionType.TELEPORT_TO_BLOCKSTATE &&
               (world.isAir(pos.up())) &&
               world.getBlockState(pos).equals(Block.getStateFromRawId(entity.getCurrentInstructionData()))
    }

    override fun canStart(): Boolean {
        return this.entity.isTamed && !this.entity.isSitting && super.canStart()
    }

    override fun start() {
//        super.start()
    }

    override fun tick() {
//        super.tick()
        entity.teleportOnTopOfBlock(this.getTargetPos())
        stop()
    }

    override fun stop() {
        entity.goalCompleted()
    }

    override fun getInterval(mob: PathAwareEntity?): Int = 0

    override fun getDesiredDistanceToTarget(): Double = 1.0
}