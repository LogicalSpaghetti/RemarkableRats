package spaghetti.remarkablerats.item

import net.minecraft.fluid.Fluids
import net.minecraft.item.Item
import net.minecraft.item.SpawnEggItem
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Identifier
import spaghetti.remarkablerats.entity.RatEntities
import spaghetti.remarkablerats.item.custom.BundleOfRatsItem
import spaghetti.remarkablerats.logger
import spaghetti.remarkablerats.mod_id

object RatItems {

    val ratatouille: Item = registerItem("ratatouille", Item(Item.Settings()))
    val bundle_of_rats: Item = registerItem("bundle_of_rats",
            BundleOfRatsItem(RatEntities.rat, Fluids.EMPTY, SoundEvents.ITEM_BUNDLE_DROP_CONTENTS,
                    Item.Settings().maxCount(1)))
    val rat_top_hat: Item = registerItem("rat_top_hat", Item(Item.Settings()))

    val rat_spawn_egg =
            registerItem("rat_spawn_egg", SpawnEggItem(RatEntities.rat, 0x656476, 0x663d3d, Item.Settings()))

    private fun registerItem(name: String, item: Item): Item {
        return Registry.register(Registries.ITEM, Identifier.of(mod_id, name), item)
    }

    fun registerRatItems() {
        logger.info("Registering Mod Items for $mod_id")
    }
}