package spaghetti.remarkablerats

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import spaghetti.remarkablerats.datagen.*

class RemarkableRatsDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val pack = fabricDataGenerator.createPack()
        pack.addProvider(::RatBlockTagProvider)
        pack.addProvider(::RatEntityTagProvider)
        pack.addProvider(::RatItemTagProvider)
        pack.addProvider(::RatLootTableProvider)
        pack.addProvider(::RatModelProvider)
        pack.addProvider(::RatRecipeProvider)
    }
}
