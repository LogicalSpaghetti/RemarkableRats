package spaghetti.remarkablerats.item

import net.minecraft.item.Item
import net.minecraft.item.SpawnEggItem
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import spaghetti.remarkablerats.entity.ModEntities
import spaghetti.remarkablerats.logger
import spaghetti.remarkablerats.mod_id

object ModItems {
    val ratatouille: Item = registerItem("ratatouille", Item(Item.Settings()));

    val rat_spawn_egg = registerItem("rat_spawn_egg",
        SpawnEggItem(
            ModEntities.rat, 0x656476, 0x663d3d, Item.Settings()));

    private fun registerItem(name: String, item: Item): Item {
        return Registry.register(Registries.ITEM, Identifier.of(mod_id, name), item);
    }

    fun registerModItems() {
        logger.info("Registering Mod Items for $mod_id")
    }
}