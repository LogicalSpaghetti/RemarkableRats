package spaghetti.remarkablerats.entity.client

import net.minecraft.client.render.entity.model.EntityModelLayer
import net.minecraft.util.Identifier
import spaghetti.remarkablerats.rat_id

object RatModelLayers {
    @JvmField
    val rat: EntityModelLayer = EntityModelLayer(
        Identifier.of(
            rat_id, "rat"
        ), "main"
    )
}
