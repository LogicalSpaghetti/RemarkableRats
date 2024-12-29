package spaghetti.remarkablerats.entity

import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.minecraft.world.World
import spaghetti.remarkablerats.entity.entities.RatEntity
import spaghetti.remarkablerats.logger
import spaghetti.remarkablerats.mod_id

object ModEntities2 {
    val rat: EntityType<RatEntity> = Registry.register<EntityType<*>, EntityType<RatEntity>>(
        Registries.ENTITY_TYPE,
        Identifier.of(mod_id, "rat"),
        EntityType.Builder.create({ entityType: EntityType<RatEntity>, world: World? ->
            RatEntity(entityType, world)
        }, SpawnGroup.CREATURE)
            .dimensions(0.6f, 0.4f).build()
    )

    fun registerModEntities() {
        logger.info("Registering Entities for $mod_id")
    }
}