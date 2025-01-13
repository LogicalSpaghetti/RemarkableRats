package spaghetti.remarkablerats.entity.abstracts

import net.minecraft.block.Block
import net.minecraft.entity.EntityType
import net.minecraft.entity.passive.TameableEntity
import net.minecraft.world.World

abstract class CommandedEntity protected constructor(entityType: EntityType<out TameableEntity>, world: World) :
        TameableEntity(entityType, world) {
    var targetedBlockType: Block? = null
}
