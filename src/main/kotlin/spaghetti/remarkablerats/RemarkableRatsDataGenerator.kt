package spaghetti.remarkablerats

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import spaghetti.remarkablerats.datagen.*
import java.util.concurrent.CompletableFuture

class RemarkableRatsDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack = fabricDataGenerator.createPack();
		pack.addProvider { output: FabricDataOutput, registriesFuture: CompletableFuture<WrapperLookup>
			-> ModBlockTagProvider(output, registriesFuture) }
		pack.addProvider { output: FabricDataOutput, completableFuture: CompletableFuture<WrapperLookup>
			-> ModItemTagProvider(output, completableFuture) }
		pack.addProvider { dataOutput: FabricDataOutput, registryLookup: CompletableFuture<WrapperLookup>
			-> ModLootTableProvider(dataOutput, registryLookup) }
		pack.addProvider { output: FabricDataOutput
			-> ModModelProvider(output) }
		pack.addProvider { output: FabricDataOutput, registriesFuture: CompletableFuture<WrapperLookup>
			-> ModRecipeProvider(output, registriesFuture) }
        pack.addProvider(::ModBlockTagProvider)
	}

	// why is it easier in Java?:
	// 		pack.addProvider(ModBlockTagProvider::new);
	//		pack.addProvider(ModItemTagProvider::new);
	//		pack.addProvider(ModLootTableProvider::new);
	//		pack.addProvider(ModModelProvider::new);
	//		pack.addProvider(ModRecipeProvider::new);
}
