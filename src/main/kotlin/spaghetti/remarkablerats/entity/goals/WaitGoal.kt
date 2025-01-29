package spaghetti.remarkablerats.entity.goals

import net.minecraft.entity.ai.goal.Goal
import spaghetti.remarkablerats.entity.abstracts.CommandedEntity
import spaghetti.remarkablerats.entity.enums.RatActionType
import java.util.*

class WaitGoal(val entity: CommandedEntity): Goal(), GoalDelay {

    override var waitDuration = 0

    init {
        this.controls = EnumSet.of(Control.MOVE)
    }

    override fun canStart(): Boolean {
        return entity.getCurrentInstructionType() == RatActionType.WAIT
    }

    override fun start() {
        waitDuration = entity.getCurrentInstructionData()
    }

    override fun tick() {
        delayTick()
    }

    override fun waitFinished() {
        entity.goalCompleted()
        start()
    }
}