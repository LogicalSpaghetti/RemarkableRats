package spaghetti.remarkablerats.entity.models

import net.minecraft.client.model.*
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.entity.model.SinglePartEntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.MathHelper
import spaghetti.remarkablerats.entity.RatAnimations.RAT_IDLE
import spaghetti.remarkablerats.entity.RatAnimations.RAT_WALK
import spaghetti.remarkablerats.entity.entities.RatEntity

class RatModel<T : RatEntity?>(root: ModelPart) : SinglePartEntityModel<T>() {
    private val rat: ModelPart = root.getChild("Rat")
    private val head: ModelPart = rat.getChild("BodyBottom").getChild("Chest").getChild("Head")

    private fun setHeadAngles(headYaw: Float, headPitch: Float) {
        var headYaw = headYaw
        var headPitch = headPitch
        headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f)
        headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f)

        head.yaw = headYaw * 0.017453292f
        head.pitch = headPitch * 0.017453292f
    }

    override fun render(matrices: MatrixStack, vertexConsumer: VertexConsumer, light: Int, overlay: Int, color: Int) {
        rat.render(matrices, vertexConsumer, light, overlay, color)
    }

    override fun getPart(): ModelPart {
        return rat
    }

    override fun setAngles(entity: T, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float,
            headPitch: Float) {
        this.part.traverse()
                .forEach { obj: ModelPart -> obj.resetTransform() }  // this line resets the transformations each time, so they don't stack
        this.setHeadAngles(netHeadYaw, headPitch)

        this.animateMovement(RAT_WALK, limbSwing, limbSwingAmount, 2f, 2.5f)
        if (entity != null) this.updateAnimation(entity.idleAnimationState, RAT_IDLE, ageInTicks, 1f)
    }

    companion object {
        val texturedModelData: TexturedModelData
            get() {
                val modelData = ModelData()
                val modelPartData = modelData.root
                val Rat = modelPartData.addChild("Rat", ModelPartBuilder.create(),
                        ModelTransform.pivot(0.0f, 24.0f, 0.0f))

                val BodyBottom = Rat.addChild("BodyBottom",
                        ModelPartBuilder.create().uv(0, 0).cuboid(-3.0f, -4.0f, -4.0f, 6.0f, 4.0f, 5.0f, Dilation(0.0f))
                                .uv(12, 9).cuboid(-2.5f, -3.5f, 1.0f, 5.0f, 3.0f, 1.0f, Dilation(0.0f)),
                        ModelTransform.pivot(0.0f, -2.0f, 2.0f))

                val Chest = BodyBottom.addChild("Chest",
                        ModelPartBuilder.create().uv(0, 9).cuboid(-2.0f, 0.0f, -4.0f, 4.0f, 4.0f, 4.0f, Dilation(0.0f)),
                        ModelTransform.pivot(0.0f, -4.0152f, -3.8264f))

                val Head = Chest.addChild("Head", ModelPartBuilder.create().uv(12, 13)
                        .cuboid(-1.5f, -1.5f, -4.0f, 3.0f, 3.0f, 4.0f, Dilation(0.0f)),
                        ModelTransform.pivot(0.0f, 2.0f, -4.0f))

                val Nose = Head.addChild("Nose", ModelPartBuilder.create().uv(13, 24)
                        .cuboid(-0.5f, -4.5f, -10.5f, 1.0f, 1.0f, 1.0f, Dilation(0.0f)),
                        ModelTransform.pivot(0.0f, 4.0f, 6.0f))

                val Ears = Head.addChild("Ears", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, -1.5f, -1.0f))

                val LeftEar =
                        Ears.addChild("LeftEar", ModelPartBuilder.create(), ModelTransform.pivot(-1.5f, -1.0f, 0.0f))

                val LeftEar_r1 = LeftEar.addChild("LeftEar_r1",
                        ModelPartBuilder.create().uv(0, 28).cuboid(0.0f, -1.0f, 0.0f, 0.0f, 2.0f, 2.0f, Dilation(0.0f)),
                        ModelTransform.of(3.0f, 0.0f, 0.0f, 0.0f, 0.7854f, 0.0f))

                val RightEar =
                        Ears.addChild("RightEar", ModelPartBuilder.create(), ModelTransform.pivot(-1.5f, -1.0f, 0.0f))

                val RightEar_r1 = RightEar.addChild("RightEar_r1",
                        ModelPartBuilder.create().uv(0, 28).cuboid(0.0f, -1.0f, 0.0f, 0.0f, 2.0f, 2.0f, Dilation(0.0f)),
                        ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, -0.7854f, 0.0f))

                val whiskers = Head.addChild("whiskers", ModelPartBuilder.create().uv(4, 27)
                        .cuboid(-3.5f, -2.5f, 0.0f, 7.0f, 5.0f, 0.0f, Dilation(0.0f)),
                        ModelTransform.pivot(0.0f, 0.0f, -4.0f))

                val FrontLegs =
                        Chest.addChild("FrontLegs", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 3.0f, -2.0f))

                val FrontLeftLeg = FrontLegs.addChild("FrontLeftLeg", ModelPartBuilder.create().uv(21, 20)
                        .cuboid(2.0f, -4.0f, -5.0f, 1.0f, 2.0f, 2.0f, Dilation(0.0f)),
                        ModelTransform.pivot(0.0f, 4.0f, 4.0f))

                val FrontLeftFoot = FrontLeftLeg.addChild("FrontLeftFoot", ModelPartBuilder.create().uv(10, 20)
                        .cuboid(-0.5f, 0.0f, -1.5f, 1.0f, 1.0f, 2.0f, Dilation(0.0f)),
                        ModelTransform.pivot(2.5f, -2.0f, -4.5f))

                val FrontRightLeg = FrontLegs.addChild("FrontRightLeg", ModelPartBuilder.create().uv(22, 11)
                        .cuboid(-3.0f, -4.0f, -5.0f, 1.0f, 2.0f, 2.0f, Dilation(0.0f)),
                        ModelTransform.pivot(0.0f, 4.0f, 4.0f))

                val FrontRightFoot = FrontRightLeg.addChild("FrontRightFoot", ModelPartBuilder.create().uv(5, 17)
                        .cuboid(-0.5f, 0.0f, -1.5f, 1.0f, 1.0f, 2.0f, Dilation(0.0f)),
                        ModelTransform.pivot(-2.5f, -2.0f, -4.5f))

                val Tail = BodyBottom.addChild("Tail", ModelPartBuilder.create().uv(22, 5)
                        .cuboid(-0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 3.0f, Dilation(0.0f)),
                        ModelTransform.pivot(0.0f, -2.0f, 2.0f))

                val tail2 = Tail.addChild("tail2", ModelPartBuilder.create().uv(18, 25)
                        .cuboid(-0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 6.0f, Dilation(0.0f)),
                        ModelTransform.pivot(0.0f, 0.0f, 3.0f))

                val BackLegs =
                        Rat.addChild("BackLegs", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, -3.0f, 1.0f))

                val BackLeftLeg = BackLegs.addChild("BackLeftLeg", ModelPartBuilder.create().uv(5, 20)
                        .cuboid(3.0f, -1.0f, -1.0f, 1.0f, 3.0f, 3.0f, Dilation(0.0f)),
                        ModelTransform.pivot(0.0f, 0.0f, 0.0f))

                val BackLeftFoot = BackLeftLeg.addChild("BackLeftFoot", ModelPartBuilder.create().uv(13, 20)
                        .cuboid(3.0f, -2.0f, -1.0f, 1.0f, 1.0f, 3.0f, Dilation(0.0f)),
                        ModelTransform.pivot(0.0f, 4.0f, -1.0f))

                val BackRightLeg = BackLegs.addChild("BackRightLeg", ModelPartBuilder.create().uv(17, 0)
                        .cuboid(-4.0f, -2.0f, -1.0f, 1.0f, 1.0f, 3.0f, Dilation(0.0f)),
                        ModelTransform.pivot(0.0f, 4.0f, -1.0f))

                val BackRightFoot = BackRightLeg.addChild("BackRightFoot", ModelPartBuilder.create().uv(0, 17)
                        .cuboid(-4.0f, -5.0f, 0.0f, 1.0f, 3.0f, 3.0f, Dilation(0.0f)),
                        ModelTransform.pivot(0.0f, 0.0f, 0.0f))
                return TexturedModelData.of(modelData, 32, 32)
            }
    }
}