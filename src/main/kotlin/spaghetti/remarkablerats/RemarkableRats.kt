package spaghetti.remarkablerats

import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spaghetti.remarkablerats.item.ModItems

val mod_id: String = "remarkablerats";
val logger: Logger = LoggerFactory.getLogger(mod_id);

object RemarkableRats : ModInitializer {

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Initializing $mod_id");
		ModItems.registerModItems();
	}
}

