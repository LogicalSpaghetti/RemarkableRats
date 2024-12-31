package spaghetti.remarkablerats.entity

import net.minecraft.client.render.entity.animation.Animation
import net.minecraft.client.render.entity.animation.AnimationHelper
import net.minecraft.client.render.entity.animation.Keyframe
import net.minecraft.client.render.entity.animation.Transformation

object RatAnimations {
    val RAT_IDLE: Animation = Animation.Builder.create(2.0f).looping()
        .addBoneAnimation(
            "Tail", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.5f,
                    AnimationHelper.createRotationalVector(0.0f, 22.5f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    1.0f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    1.5f,
                    AnimationHelper.createRotationalVector(0.0f, -22.5f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    2.0f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "tail2", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    1.0f,
                    AnimationHelper.createRotationalVector(90.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    2.0f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "Ears", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    1.0f,
                    AnimationHelper.createRotationalVector(180.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    2.0f,
                    AnimationHelper.createRotationalVector(360.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "Ears", Transformation(
                Transformation.Targets.SCALE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createScalingVector(1.0, 1.0, 1.0),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(2.0f, AnimationHelper.createScalingVector(1.0, 1.0, 1.0), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "LeftEar", Transformation(
                Transformation.Targets.SCALE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createScalingVector(1.0, 1.0, 1.0),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(2.0f, AnimationHelper.createScalingVector(1.0, 1.0, 1.0), Transformation.Interpolations.LINEAR)
            )
        )
        .addBoneAnimation(
            "whiskers", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 177.5f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.5f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 357.5f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.75f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 530.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    1.0f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 710.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    1.25f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 882.5f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    1.5f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 1062.5f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    1.75f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 1240.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    2.0f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 1420.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .build()

    val RAT_STAND: Animation = Animation.Builder.create(9.0833f).looping()
        .addBoneAnimation(
            "Head", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(40.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "FrontLegs", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(60.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "Tail", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(47.5f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "Tail", Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createTranslationalVector(0.0f, 1.5f, -0.5f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "tail2", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(25.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "BodyBottom", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(-75.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "BodyBottom", Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createTranslationalVector(0.0f, -0.2f, -2.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "FrontLeftFoot", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(22.5f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "FrontRightFoot", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(22.5f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "Rat", Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createTranslationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "Chest", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .build()

    val RAT_WALK: Animation = Animation.Builder.create(0.4167f).looping()
        .addBoneAnimation(
            "Head", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.0833f,
                    AnimationHelper.createRotationalVector(-12.5f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.1667f,
                    AnimationHelper.createRotationalVector(10.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f,
                    AnimationHelper.createRotationalVector(-25.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.4167f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "FrontLegs", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(-12.5f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.0833f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.1667f,
                    AnimationHelper.createRotationalVector(-25.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.4167f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "BackLegs", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.1667f,
                    AnimationHelper.createRotationalVector(25.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.4167f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "Tail", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(-10.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.1667f,
                    AnimationHelper.createRotationalVector(10.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.4167f,
                    AnimationHelper.createRotationalVector(-10.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "tail2", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(22.5f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.1667f,
                    AnimationHelper.createRotationalVector(-22.5f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.4167f,
                    AnimationHelper.createRotationalVector(22.5f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "BodyBottom", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.0833f,
                    AnimationHelper.createRotationalVector(-12.5f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.1667f,
                    AnimationHelper.createRotationalVector(-7.5f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.4167f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "Rat", Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createTranslationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.0833f,
                    AnimationHelper.createTranslationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.1667f,
                    AnimationHelper.createTranslationalVector(0.0f, 0.75f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f,
                    AnimationHelper.createTranslationalVector(0.0f, 0.75f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.4167f,
                    AnimationHelper.createTranslationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .addBoneAnimation(
            "Chest", Transformation(
                Transformation.Targets.ROTATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.0833f,
                    AnimationHelper.createRotationalVector(12.5f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.1667f,
                    AnimationHelper.createRotationalVector(12.5f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.25f,
                    AnimationHelper.createRotationalVector(20.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    0.4167f,
                    AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .build()

    val jointedHeight: Animation = Animation.Builder.create(1.0f).looping()
        .addBoneAnimation(
            "main", Transformation(
                Transformation.Targets.TRANSLATE,
                Keyframe(
                    0.0f,
                    AnimationHelper.createTranslationalVector(0.0f, -32.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                ),
                Keyframe(
                    1.0f,
                    AnimationHelper.createTranslationalVector(0.0f, 0.0f, 0.0f),
                    Transformation.Interpolations.LINEAR
                )
            )
        )
        .build()
}
