package spaghetti.remarkablerats.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.BlockTagProvider
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.registry.tag.BlockTags
import spaghetti.remarkablerats.block.ModBlocks
import java.util.concurrent.CompletableFuture

class ModBlockTagProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<WrapperLookup>)
    : BlockTagProvider(output, registriesFuture) {
    override fun configure(arg: WrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
            .add(ModBlocks.morton_pink_granite);
    }
}

