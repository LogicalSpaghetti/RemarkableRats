package spaghetti.remarkablerats.block

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.MapColor
import net.minecraft.block.enums.NoteBlockInstrument
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import spaghetti.remarkablerats.item.ModItems.ratatouille
import spaghetti.remarkablerats.logger
import spaghetti.remarkablerats.mod_id

object ModBlocks {

    val morton_pink_granite: Block = registerBlock("morton_pink_granite",
        Block(
            AbstractBlock.Settings.create().
            mapColor(MapColor.DIRT_BROWN).instrument(NoteBlockInstrument.BASEDRUM)
                .requiresTool().strength(1.5f, 6.0f)));

    private fun registerBlock(name: String, block: Block) : Block {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(mod_id, name), block);
    }

    private fun registerBlockItem(name: String, block: Block) {
        Registry.register(Registries.ITEM, Identifier.of(mod_id, name),
            BlockItem(block, Item.Settings()));
    }

    fun registerModBlocks() {
        logger.info("Registering Mod Blocks for $mod_id");

//        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS)
//            .register(ModifyEntries { entries: FabricItemGroupEntries ->
//                entries.add(morton_pink_granite)
//            });
    }
}