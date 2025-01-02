package spaghetti.remarkablerats.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.Model
import net.minecraft.data.client.Models
import net.minecraft.util.Identifier
import spaghetti.remarkablerats.block.RatBlocks
import spaghetti.remarkablerats.item.RatItems
import java.util.*

class RatModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(RatBlocks.morton_pink_granite);
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
        itemModelGenerator.register(RatItems.ratatouille, Models.GENERATED);
        itemModelGenerator.register(RatItems.rat_top_hat, Models.GENERATED);
        itemModelGenerator.register(RatItems.bundle_of_rats, Models.GENERATED);

        itemModelGenerator.register(
            RatItems.rat_spawn_egg,
            Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));
    }
}

