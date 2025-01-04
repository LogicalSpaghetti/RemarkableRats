package spaghetti.remarkablerats.sound

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
import spaghetti.remarkablerats.id
import spaghetti.remarkablerats.logger
import spaghetti.remarkablerats.mod_id

object RatSounds {
    private val rat_squeak: Identifier = id("rat_squeak_1")
    val rat_squeak_event: SoundEvent = SoundEvent.of(rat_squeak)

    fun registerRatSounds() {
        logger.info("Registering Sounds for $mod_id")
        Registry.register(Registries.SOUND_EVENT, rat_squeak, rat_squeak_event)
    }
}
