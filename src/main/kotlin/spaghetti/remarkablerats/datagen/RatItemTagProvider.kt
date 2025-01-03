package spaghetti.remarkablerats.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.item.Items
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import spaghetti.remarkablerats.tags.RatTags.Items.rat_consumable_items
import java.util.concurrent.CompletableFuture


class RatItemTagProvider(output: FabricDataOutput, completableFuture: CompletableFuture<WrapperLookup>) :
        FabricTagProvider.ItemTagProvider(output, completableFuture) {
    override fun configure(arg: WrapperLookup) {
        getOrCreateTagBuilder(rat_consumable_items)
                .add(Items.POTATO)
                .add(Items.CARROT)
                .add(Items.WHEAT)
                .add(Items.BEETROOT)
                .add(Items.RED_MUSHROOM)
    }
}
