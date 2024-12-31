package spaghetti.remarkablerats.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import spaghetti.remarkablerats.block.ModBlocks
import java.util.concurrent.CompletableFuture

class ModLootTableProvider(dataOutput: FabricDataOutput, registryLookup: CompletableFuture<WrapperLookup>)
    : FabricBlockLootTableProvider(dataOutput, registryLookup) {
    override fun generate() {
        addDrop(ModBlocks.morton_pink_granite);
    }
}
