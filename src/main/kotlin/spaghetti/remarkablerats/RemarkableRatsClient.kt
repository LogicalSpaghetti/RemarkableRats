package spaghetti.remarkablerats

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.render.entity.EntityRendererFactory
import spaghetti.remarkablerats.entity.ModEntities
import spaghetti.remarkablerats.entity.client.ModModelLayers
import spaghetti.remarkablerats.entity.entities.RatModel
import spaghetti.remarkablerats.entity.entities.RatRenderer

object RemarkableRatsClient: ClientModInitializer {
    override fun onInitializeClient() {

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.rat)
            { RatModel.texturedModelData };

        EntityRendererRegistry.register(ModEntities.rat)
            { context: EntityRendererFactory.Context? -> RatRenderer(context!!) };
    }
}