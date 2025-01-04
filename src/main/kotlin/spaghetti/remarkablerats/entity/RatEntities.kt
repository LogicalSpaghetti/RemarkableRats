package spaghetti.remarkablerats.entity

import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import spaghetti.remarkablerats.entity.entities.RatEntity
import spaghetti.remarkablerats.id
import spaghetti.remarkablerats.logger
import spaghetti.remarkablerats.mod_id

object RatEntities {
    val rat: EntityType<RatEntity> = Registry.register(Registries.ENTITY_TYPE, id("rat"),
            EntityType.Builder.create(::RatEntity, SpawnGroup.CREATURE).dimensions(0.6f, 0.4f).build())

    fun registerRatEntities() {
        logger.info("Registering Entities for $mod_id")
        registerEntityAttributes()
    }

    private fun registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(rat, RatEntity.createAttributes())
    }
}