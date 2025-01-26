package spaghetti.remarkablerats.entity.goals

import spaghetti.remarkablerats.logger

interface GoalDelay {
    var waitDuration: Int

    fun getDelayAfterAction() = 20

    fun delayTick() {
        if (waitDuration > 0) {
            logger.info("Delay decreased to $waitDuration")
            waitDuration--
        } else {
            waitFinished()
        }
    }

    fun waitFinished()
}