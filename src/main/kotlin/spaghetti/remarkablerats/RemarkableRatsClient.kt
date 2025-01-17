package spaghetti.remarkablerats

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.gui.screen.ingame.HandledScreens
import spaghetti.remarkablerats.data.RatModelPredicates
import spaghetti.remarkablerats.entity.RatEntities
import spaghetti.remarkablerats.entity.RatModelLayers
import spaghetti.remarkablerats.entity.models.RatModel
import spaghetti.remarkablerats.entity.renderers.RatRenderer
import spaghetti.remarkablerats.screen.RatEntityScreen
import spaghetti.remarkablerats.screen.RatScreenHandlers

object RemarkableRatsClient : ClientModInitializer {
    override fun onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(RatModelLayers.rat) { RatModel.texturedModelData }
        EntityRendererRegistry.register(RatEntities.rat, ::RatRenderer)

        HandledScreens.register(RatScreenHandlers.ratScreenHandler, ::RatEntityScreen);

        RatModelPredicates.registerModelPredicates()
    }
}