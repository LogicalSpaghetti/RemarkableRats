package spaghetti.remarkablerats.entity.renderers.layers;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import spaghetti.remarkablerats.entity.entities.RatEntity;
import spaghetti.remarkablerats.entity.models.RatModel;

public class RatHatLayer extends FeatureRenderer<RatEntity, RatModel<RatEntity>> {

    public RatHatLayer(FeatureRendererContext<RatEntity, RatModel<RatEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, RatEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {

    }
}
