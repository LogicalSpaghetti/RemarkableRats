package spaghetti.remarkablerats.data

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import spaghetti.remarkablerats.id

class RatTags {
    object Blocks {
        private fun createTag(name: String): TagKey<Block> {
            return TagKey.of(RegistryKeys.BLOCK, id(name))
        }
    }

    object Items {
        val rat_consumable_items: TagKey<Item> = createTag("rat_consumable_items")
        private fun createTag(name: String): TagKey<Item> {
            return TagKey.of(RegistryKeys.ITEM, id(name))
        }
    }
}