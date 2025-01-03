package spaghetti.remarkablerats.item

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import spaghetti.remarkablerats.block.RatBlocks
import spaghetti.remarkablerats.logger
import spaghetti.remarkablerats.mod_id


object RatItemGroups {
    private val remarkablerats_item_group: ItemGroup =
            Registry.register(Registries.ITEM_GROUP, Identifier.of(mod_id, "remarkablerats_group"),
                    FabricItemGroup.builder().icon { ItemStack(RatItems.ratatouille) }
                            .displayName(Text.translatable("itemgroup.remarkablerats.remarkablerats_group"))
                            .entries { _: ItemGroup.DisplayContext, entries: ItemGroup.Entries ->
                                entries.add(RatItems.rat_spawn_egg)
                                entries.add(RatItems.ratatouille)
                                entries.add(RatItems.bundle_of_rats)
                                entries.add(RatItems.rat_top_hat)
                                entries.add(RatBlocks.morton_pink_granite)
                            }.build())

    fun registerRatItemGroups() {
        logger.info("Registering $remarkablerats_item_group for $mod_id") }
}