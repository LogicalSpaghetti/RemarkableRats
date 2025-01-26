package spaghetti.remarkablerats.entity.goals

import net.minecraft.entity.ai.goal.Goal
import spaghetti.remarkablerats.entity.abstracts.CommandedEntity
import spaghetti.remarkablerats.entity.enums.RatActionType

class WaitGoal(val entity: CommandedEntity): Goal(), GoalDelay {

    override var waitDuration = 0

    override fun canStart(): Boolean { return entity.getCurrentInstructionType() == RatActionType.WAIT }

    override fun start() { waitDuration = entity.getCurrentInstructionData() }

    override fun tick() { delayTick() }

    override fun waitFinished() { stop() }

    override fun stop() { entity.goalCompleted() }
}