package spaghetti.remarkablerats.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.EntityTypeTagProvider
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.registry.tag.EntityTypeTags
import spaghetti.remarkablerats.entity.RatEntities
import java.util.concurrent.CompletableFuture

class RatEntityTagProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<WrapperLookup>) :
        EntityTypeTagProvider(output, registriesFuture) {
    override fun configure(arg: WrapperLookup) {
        getOrCreateTagBuilder(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add(RatEntities.rat)
        getOrCreateTagBuilder(EntityTypeTags.CAN_TURN_IN_BOATS).add(RatEntities.rat)
        getOrCreateTagBuilder(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(RatEntities.rat)
        getOrCreateTagBuilder(EntityTypeTags.IMMUNE_TO_INFESTED).add(RatEntities.rat)
        getOrCreateTagBuilder(EntityTypeTags.SENSITIVE_TO_IMPALING).add(RatEntities.rat)
    }
}

