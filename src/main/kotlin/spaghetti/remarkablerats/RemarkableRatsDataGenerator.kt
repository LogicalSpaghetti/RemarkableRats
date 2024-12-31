package spaghetti.remarkablerats

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import spaghetti.remarkablerats.datagen.*

class RemarkableRatsDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack = fabricDataGenerator.createPack();
		pack.addProvider(::ModBlockTagProvider);
		pack.addProvider(::ModItemTagProvider);
		pack.addProvider(::ModLootTableProvider);
		pack.addProvider(::ModModelProvider);
		pack.addProvider(::ModRecipeProvider);
	}
}
