package spaghetti.remarkablerats.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.Model
import net.minecraft.data.client.Models
import net.minecraft.util.Identifier
import spaghetti.remarkablerats.block.ModBlocks
import spaghetti.remarkablerats.item.ModItems
import java.util.*

class ModModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.morton_pink_granite);
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
        itemModelGenerator.register(ModItems.ratatouille, Models.GENERATED);

        itemModelGenerator.register(
            ModItems.rat_spawn_egg,
            Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));
    }
}

