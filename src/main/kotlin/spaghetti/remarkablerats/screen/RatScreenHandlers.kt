package spaghetti.remarkablerats.screen

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType.ExtendedFactory
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.packet.CustomPayload
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier
import spaghetti.remarkablerats.logger
import spaghetti.remarkablerats.mod_id
import spaghetti.remarkablerats.network.EntityIdPayload.Companion.PACKET_CODEC

object RatScreenHandlers {
    val ratScreenHandler: ScreenHandlerType<RatEntityScreenHandler> =
            register("rat_screen", ::RatEntityScreenHandler, PACKET_CODEC)

    private fun <T : ScreenHandler, D : CustomPayload> register(name: String, factory: ExtendedFactory<T, D>,
            codec: PacketCodec<in RegistryByteBuf, D>): ExtendedScreenHandlerType<T, D> {
        return Registry.register(Registries.SCREEN_HANDLER,
                Identifier.of(mod_id, name),
                ExtendedScreenHandlerType(factory, codec))
    }

    fun registerScreenHandlers() {
        logger.info("Registering Screen Handlers for $mod_id")
    }
}
