package spaghetti.remarkablerats.entity.goals

import spaghetti.remarkablerats.logger

interface GoalDelay {
    var waitDuration: Int

    fun getDelayAfterAction() = 4

    fun delayTick() {
        if (waitDuration > 0) {
            logger.info("Delay decreased to $waitDuration for class ${this.javaClass}")
            waitDuration--
        } else {
            waitFinished()
        }
    }

    fun waitFinished()
}