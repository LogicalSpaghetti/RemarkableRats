package spaghetti.remarkablerats.block

import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.MapColor
import net.minecraft.block.enums.NoteBlockInstrument
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import spaghetti.remarkablerats.logger
import spaghetti.remarkablerats.rat_id

object RatBlocks {

    val morton_pink_granite: Block = registerBlock("morton_pink_granite",
        Block(
            AbstractBlock.Settings.create().
            mapColor(MapColor.DIRT_BROWN).instrument(NoteBlockInstrument.BASEDRUM)
                .requiresTool().strength(1.5f, 6.0f)));

    private fun registerBlock(name: String, block: Block) : Block {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(rat_id, name), block);
    }

    private fun registerBlockItem(name: String, block: Block) {
        Registry.register(Registries.ITEM, Identifier.of(rat_id, name),
            BlockItem(block, Item.Settings()));
    }

    fun registerRatBlocks() { logger.info("Registering Mod Blocks for $rat_id"); }
}
