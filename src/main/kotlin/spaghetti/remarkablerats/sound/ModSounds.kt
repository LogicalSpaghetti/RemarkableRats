package spaghetti.remarkablerats.sound

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier

object ModSounds {
    val rat_squeak: Identifier = Identifier.of("remarkablerats:rat_squeak_1");
    val rat_squeak_event: SoundEvent = SoundEvent.of(rat_squeak);


    fun registerSounds() {
        Registry.register(Registries.SOUND_EVENT, rat_squeak, rat_squeak_event);
    }
}
