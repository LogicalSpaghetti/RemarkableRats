package spaghetti.remarkablerats

import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spaghetti.remarkablerats.block.RatBlocks
import spaghetti.remarkablerats.entity.RatEntities
import spaghetti.remarkablerats.item.RatItemGroups
import spaghetti.remarkablerats.item.RatItems
import spaghetti.remarkablerats.screen.RatScreenHandlers
import spaghetti.remarkablerats.sound.RatSounds

const val mod_id: String = "remarkablerats"
val logger: Logger = LoggerFactory.getLogger(mod_id)

object RemarkableRats : ModInitializer {
    override fun onInitialize() {
        logger.info("Initializing $mod_id")

        RatItemGroups.registerRatItemGroups()
        RatSounds.registerRatSounds()

        RatItems.registerRatItems()
        RatBlocks.registerRatBlocks()

        RatEntities.registerRatEntities()

        RatScreenHandlers.registerScreenHandlers()
    }
}
