package spaghetti.remarkablerats.tags

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import spaghetti.remarkablerats.mod_id

class RatTags {
    object Blocks {
        private fun createTag(name: String): TagKey<Block> {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(mod_id, name))
        }
    }

    object Items {
        val rat_consumable_items: TagKey<Item> = createTag("rat_consumable_items")
        private fun createTag(name: String): TagKey<Item> {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(mod_id, name))
        }
    }
}