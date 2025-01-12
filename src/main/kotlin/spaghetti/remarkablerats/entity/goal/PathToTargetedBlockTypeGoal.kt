package spaghetti.remarkablerats.entity.goal

import net.minecraft.entity.ai.goal.Goal
import net.minecraft.entity.mob.PathAwareEntity

class PathToTargetedBlockTypeGoal(entity: PathAwareEntity): Goal() {
    override fun canStart(): Boolean {
        return false
    }
}