package spaghetti.remarkablerats

import net.fabricmc.api.ClientModInitializer

object RemarkableRatsClient: ClientModInitializer {
    override fun onInitializeClient() {
        logger.info("Initializing $mod_id Client");
    }
}