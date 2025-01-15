package spaghetti.remarkablerats.entity.goals

import net.minecraft.entity.ai.goal.MoveToTargetPosGoal
import net.minecraft.entity.mob.PathAwareEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.WorldView
import spaghetti.remarkablerats.entity.abstracts.CommandedEntity

class PathToTargetedBlockTypeGoal(val entity: CommandedEntity, speed: Double, range: Int): MoveToTargetPosGoal(entity, speed, range) {

    override fun isTargetPos(world: WorldView, pos: BlockPos): Boolean {
        return world.isAir(pos.up()) && world.getBlockState(pos).equals(entity.targetedBlockType)
    }

    override fun canStart(): Boolean {
        return this.entity.isTamed && !this.entity.isSitting && super.canStart()
    }

    override fun start() {
        super.start()
        this.entity.setInSittingPose(false)
    }

    override fun stop() {
        super.stop()
        this.entity.setInSittingPose(false)
    }

    override fun tick() {
        super.tick()
        if (this.hasReached()) {

        }
        this.entity.setInSittingPose(this.hasReached())
    }

    override fun getInterval(mob: PathAwareEntity?): Int = 0

    override fun getDesiredDistanceToTarget(): Double = 0.0
}