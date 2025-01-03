package spaghetti.remarkablerats.entity.client

import net.minecraft.client.render.entity.model.EntityModelLayer
import net.minecraft.util.Identifier
import spaghetti.remarkablerats.mod_id

object RatModelLayers {
    @JvmField
    val rat: EntityModelLayer = EntityModelLayer(Identifier.of(mod_id, "rat"), "main")
}
