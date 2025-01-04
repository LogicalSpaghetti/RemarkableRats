package spaghetti.remarkablerats.block

import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.MapColor
import net.minecraft.block.enums.NoteBlockInstrument
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import spaghetti.remarkablerats.id
import spaghetti.remarkablerats.logger
import spaghetti.remarkablerats.mod_id

object RatBlocks {

    val morton_pink_granite: Block = registerBlock("morton_pink_granite",
            Block(AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresTool().strength(1.5f, 6.0f)))

    private fun registerBlock(name: String, block: Block): Block {
        registerBlockItem(name, block)
        return Registry.register(Registries.BLOCK, id(name), block)
    }

    private fun registerBlockItem(name: String, block: Block) {
        Registry.register(Registries.ITEM, id(name), BlockItem(block, Item.Settings()))
    }

    fun registerRatBlocks() {
        logger.info("Registering Mod Blocks for $mod_id") }
}
