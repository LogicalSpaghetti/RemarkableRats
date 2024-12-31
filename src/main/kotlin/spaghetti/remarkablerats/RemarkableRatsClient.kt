package spaghetti.remarkablerats

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import spaghetti.remarkablerats.entity.RatEntities
import spaghetti.remarkablerats.entity.client.RatModelLayers
import spaghetti.remarkablerats.entity.entities.RatModel
import spaghetti.remarkablerats.entity.entities.RatRenderer

object RemarkableRatsClient: ClientModInitializer {
    override fun onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(RatModelLayers.rat)
            { RatModel.texturedModelData };
        EntityRendererRegistry.register(RatEntities.rat, ::RatRenderer);
    }
}