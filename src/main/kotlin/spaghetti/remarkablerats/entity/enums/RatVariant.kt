package spaghetti.remarkablerats.entity.enums

import net.minecraft.util.Util
import net.minecraft.util.function.ValueLists
import net.minecraft.util.math.random.Random
import java.util.function.IntFunction
import kotlin.enums.enumEntries

// TODO: there's probably a more abstract way of handling this that allows for custom variants
enum class RatVariant(val id: Int, val color: String) {
    // every new variant needs to be listed here,
    // given a texture,
    // given a json for spawning locations,
    // added to getVariantFromPos,
    // and needs its biomes added to ModEntityGeneration
    BROWN(0, "brown"),
    TUXEDO(1, "tuxedo"),
    WHITE(2, "white"),
    BLACK(3, "black");

    companion object {
        private val BY_ID: IntFunction<RatVariant> =
                ValueLists.createIdToValueFunction({ obj: RatVariant -> obj.id }, entries.toTypedArray(),
                        ValueLists.OutOfBoundsHandling.ZERO)

        fun byId(id: Int): RatVariant {
            return BY_ID.apply(id)
        }

        fun getRandom(random: Random): RatVariant {
            val variants = enumEntries<RatVariant>()
            return Util.getRandom(variants, random)
        }
    }
}
