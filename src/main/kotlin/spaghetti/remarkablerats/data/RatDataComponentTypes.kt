package spaghetti.remarkablerats.data

import com.mojang.serialization.Codec
import net.minecraft.block.BlockState
import net.minecraft.component.ComponentType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.DyeColor
import spaghetti.remarkablerats.id
import spaghetti.remarkablerats.logger
import spaghetti.remarkablerats.mod_id
import java.util.function.UnaryOperator

object RatDataComponentTypes {
    val color: ComponentType<DyeColor> =
            register("color") { builder: ComponentType.Builder<DyeColor> -> builder.codec(DyeColor.CODEC) }
    val blockState: ComponentType<BlockState> =
            register("block") { builder: ComponentType.Builder<BlockState> -> builder.codec(BlockState.CODEC) }

    // TODO: Combine the string and int into one object
    val rat_action_int_list: ComponentType<List<Int>> =
            register("action_int_list")
            { builder: ComponentType.Builder<List<Int>> -> builder.codec(Codec.INT.listOf()).cache() }
    val rat_action_string_list: ComponentType<List<String>> =
            register("action_type_list")
            { builder: ComponentType.Builder<List<String>> -> builder.codec(Codec.STRING.listOf()).cache() }

    private fun <T> register(name: String, builderOperator: UnaryOperator<ComponentType.Builder<T>>): ComponentType<T> {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id(name),
                builderOperator.apply(ComponentType.builder()).build())
    }

    fun registerDataComponentTypes() {
        logger.info("Registering DataComponentTypes for $mod_id")
    }
}
