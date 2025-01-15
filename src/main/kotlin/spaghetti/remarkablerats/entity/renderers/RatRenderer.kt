package spaghetti.remarkablerats.entity.renderers

import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import spaghetti.remarkablerats.entity.RatModelLayers
import spaghetti.remarkablerats.entity.entities.RatEntity
import spaghetti.remarkablerats.entity.models.RatModel
import spaghetti.remarkablerats.id

class RatRenderer(context: EntityRendererFactory.Context) :
        MobEntityRenderer<RatEntity, RatModel<RatEntity>>(context, RatModel(context.getPart(RatModelLayers.rat)),
                0.3f) {

    override fun getTexture(rat: RatEntity): Identifier {
        return id("textures/entity/" + rat.getVariant().color + "_rat.png")
    }

    override fun render(mobEntity: RatEntity, f: Float, g: Float, matrixStack: MatrixStack,
            vertexConsumerProvider: VertexConsumerProvider, i: Int) {
        if (mobEntity.isBaby) {
            matrixStack.scale(0.5f, 0.5f, 0.5f)
        } else {
            matrixStack.scale(1f, 1f, 1f) // is this necessary?
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i)
    }
}
