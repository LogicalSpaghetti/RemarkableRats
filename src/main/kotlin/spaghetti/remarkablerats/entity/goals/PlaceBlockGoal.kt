package spaghetti.remarkablerats.entity.goals

import net.minecraft.entity.ai.goal.Goal
import net.minecraft.sound.SoundEvents
import spaghetti.remarkablerats.block.RatBlocks
import spaghetti.remarkablerats.entity.abstracts.CommandedEntity
import spaghetti.remarkablerats.entity.enums.RatActionType
import spaghetti.remarkablerats.logger
import java.util.*

class PlaceBlockGoal(val entity: CommandedEntity) : Goal(), GoalDelay {

    override var waitDuration: Int = 0

    init {
        this.controls = EnumSet.of(Control.MOVE, Control.LOOK, Control.TARGET, Control.JUMP)
    }

    override fun canStart(): Boolean {
        return entity.getCurrentInstructionType() == RatActionType.PLACE_BLOCK
    }

    override fun start() {
        placeBlock()
        waitDuration = getDelayAfterAction()
    }

    override fun tick() {
        delayTick()
    }

    override fun waitFinished() {
        entity.goalCompleted()
        waitDuration = getDelayAfterAction()
    }

    private fun placeBlock() {
        val world = entity.world
        world.setBlockState(entity.blockPos.up(), RatBlocks.morton_pink_granite.defaultState)
        entity.playSound(SoundEvents.BLOCK_STONE_PLACE, 1.0f, 1.0f)
        logger.info("Placed block at: ${entity.blockPos.up()}")
    }
}
