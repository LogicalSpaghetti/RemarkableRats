package spaghetti.remarkablerats.entity.goals

import net.minecraft.block.Blocks
import net.minecraft.entity.ai.goal.Goal
import net.minecraft.sound.SoundEvents
import spaghetti.remarkablerats.entity.abstracts.CommandedEntity
import spaghetti.remarkablerats.entity.enums.RatActionType
import spaghetti.remarkablerats.logger

class BreakBlockGoal(val entity: CommandedEntity) : Goal(), GoalDelay {

    override var waitDuration: Int = 0

    override fun canStart(): Boolean { return entity.getCurrentInstructionType() == RatActionType.BREAK_BLOCK }

    override fun start() {
        breakBlock()
        waitDuration = getDelayAfterAction()
    }

    override fun tick() { delayTick() }

    override fun waitFinished() { logger.info("${this.javaClass}"); stop() }

    private fun breakBlock() {
        val world = entity.world
        world.setBlockState(entity.blockPos.up(), Blocks.AIR.defaultState)
        entity.playSound(SoundEvents.BLOCK_STONE_BREAK, 1.0f, 1.0f)
    }

    override fun stop() { entity.goalCompleted() }
}
