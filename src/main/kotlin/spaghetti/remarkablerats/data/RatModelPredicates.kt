package spaghetti.remarkablerats.data

import net.minecraft.client.item.ClampedModelPredicateProvider
import net.minecraft.client.item.ModelPredicateProviderRegistry
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import spaghetti.remarkablerats.data.RatDataComponentTypes.color
import spaghetti.remarkablerats.id
import spaghetti.remarkablerats.item.RatItems.rat_top_hat

object RatModelPredicates {
    fun registerModelPredicates() {
        ModelPredicateProviderRegistry.register(rat_top_hat, id("color"),
                (ClampedModelPredicateProvider { stack: ItemStack, _: ClientWorld?, _: LivingEntity?, _: Int ->
                    (stack.get(color)?.id?.toFloat() ?: 15f)/16f
                }))
    }
}
