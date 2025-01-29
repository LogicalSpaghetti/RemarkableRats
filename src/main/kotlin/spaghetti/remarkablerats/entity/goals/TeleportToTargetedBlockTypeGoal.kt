package spaghetti.remarkablerats.entity.goals

import net.minecraft.block.Block
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal
import net.minecraft.entity.mob.PathAwareEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.WorldView
import spaghetti.remarkablerats.entity.abstracts.CommandedEntity
import spaghetti.remarkablerats.entity.enums.RatActionType
import spaghetti.remarkablerats.logger

class TeleportToTargetedBlockTypeGoal(val entity: CommandedEntity, speed: Double, range: Int): MoveToTargetPosGoal(entity, speed, range), GoalDelay {

    override var waitDuration: Int = 0

    override fun canStart(): Boolean {
        return (entity.getCurrentInstructionType() == RatActionType.TELEPORT_TO_BLOCKSTATE) &&
               this.findTargetPos()
    }

    override fun start() {
        logger.info("Teleported ${if(entity.teleportOnTopOfBlock(this.getTargetPos())){""}else{"un"}}successfully!")
        waitDuration = getDelayAfterAction()
    }

    override fun shouldContinue(): Boolean {
        return canStart()
    }

    override fun isTargetPos(world: WorldView, pos: BlockPos): Boolean {
        return (world.isAir(pos.up())) &&
               world.getBlockState(pos).equals(Block.getStateFromRawId(entity.getCurrentInstructionData()))
    }

    override fun tick() {
        delayTick()
    }

    override fun waitFinished() {
        entity.goalCompleted()
        waitDuration = getDelayAfterAction()
    }

    override fun getInterval(mob: PathAwareEntity?): Int = 0
}
