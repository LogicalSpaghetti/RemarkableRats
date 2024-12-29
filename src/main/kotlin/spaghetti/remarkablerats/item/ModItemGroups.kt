package spaghetti.remarkablerats.item

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import spaghetti.remarkablerats.block.ModBlocks
import spaghetti.remarkablerats.logger
import spaghetti.remarkablerats.mod_id
import java.util.function.Supplier


object ModItemGroups {
    val remarkablerats_group: ItemGroup = Registry.register(Registries.ITEM_GROUP,
        Identifier.of(mod_id, "remarkablerats_group"),
        FabricItemGroup.builder().icon { ItemStack(ModItems.ratatouille) }
            .displayName(Text.translatable("itemgroup.remarkablerats.remarkablerats_group"))
            .entries { _: ItemGroup.DisplayContext, entries: ItemGroup.Entries ->
                entries.add(ModItems.ratatouille);
                entries.add(ModBlocks.morton_pink_granite);
            }.build());

    fun registerItemGroups() {
        logger.info("Registering Item Groups for $mod_id");

    }
}