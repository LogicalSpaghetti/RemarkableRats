package spaghetti.remarkablerats.entity.goal

import net.minecraft.entity.ai.goal.Goal
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import spaghetti.remarkablerats.entity.abstracts.CommandedEntity
import spaghetti.remarkablerats.logger
import java.util.*

class PathToTargetedBlockTypeGoal(val entity: CommandedEntity): Goal() {
    private val world: World;
    init {
        this.controls = EnumSet.of(Control.MOVE, Control.LOOK)
        this.world = entity.world
    }

    override fun canStart(): Boolean {
        return true
    }

    override fun start() {
        if (entity.targetedBlockType == null)
            return
        if (world.getBlockState(BlockPos(entity.blockPos.add(0, -1, 0)))
                .block.equals(entity.targetedBlockType))
            return
        val dest: BlockPos = findNearestValidBlockPos().add(0, 1, 0)
        this.entity.navigation.startMovingTo(dest.x.toDouble(), dest.y.toDouble(), dest.z.toDouble(), 1.0)
    }

    private fun findNearestValidBlockPos(): BlockPos {

        var x: Int;
        var y: Int;
        var z: Int;
        var pos: BlockPos?;
        for(yPrime in 0.. 20) {
            y = if (yPrime % 2 == 0) { yPrime / 2} else { -(yPrime + 1) / 2};
            for (zPrime in 0..20) {
                z = if (zPrime % 2 == 0) { zPrime / 2} else { -(zPrime + 1) / 2};
                for(xPrime in 0..20) {
                    x = if (xPrime % 2 == 0) { xPrime / 2} else { -(xPrime + 1) / 2};

                    pos = entity.blockPos.add(BlockPos(x, y, z))
                    if (world.getBlockState(pos).block.equals(entity.targetedBlockType)) {
                        logger.info("Found block at: ${BlockPos(x, y, z)}")
                        return pos
                    }
                }
            }
        }
        return entity.blockPos // TODO: make it fail in canStart if there's no block found
    }

    override fun shouldContinue(): Boolean {
        return !this.entity.navigation.isIdle
    }
}