package spaghetti.remarkablerats.item.custom

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.registry.tag.BlockTags
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.DyeColor
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import spaghetti.remarkablerats.data.RatDataComponentTypes
import spaghetti.remarkablerats.entity.enums.RatActionType

class RatTopHatItem(settings: Settings) : Item(settings) {

    override fun getDefaultStack(): ItemStack {
        return super.getDefaultStack().also { it.set(RatDataComponentTypes.color, DyeColor.BLACK) }
    }

    override fun appendTooltip(stack: ItemStack, context: TooltipContext, tooltip: MutableList<Text>,
            type: TooltipType?) {
        super.appendTooltip(stack, context, tooltip, type)
        tooltip.add(Text.literal("${stack.get(RatDataComponentTypes.color)}"))
        tooltip.add(Text.literal("${stack.get(RatDataComponentTypes.rat_action_string_list)}"))
        tooltip.add(Text.literal("${stack.get(RatDataComponentTypes.rat_action_int_list)}"))
    }

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (user.isSneaking) {
            val stack = user.getStackInHand(hand)
            var color = stack.get(RatDataComponentTypes.color)
            if (color == null) color = DyeColor.BLACK
            val colorId = (color.id + 1) % 16
            stack.set(RatDataComponentTypes.color, DyeColor.byId(colorId))
        }

        return super.use(world, user, hand)
    }

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val stack = context.stack
        val blockState = context.world.getBlockState(context.blockPos)

        val data = Block.getRawIdFromState(blockState)

        stack.set(RatDataComponentTypes.rat_action_int_list, ArrayList<Int>().also {
            al -> stack.get(RatDataComponentTypes.rat_action_int_list)?.forEach {
                i -> al.add(i)
            };
            al.add(data)
        })

        stack.set(RatDataComponentTypes.rat_action_string_list, ArrayList<String>().also {
            al -> stack.get(RatDataComponentTypes.rat_action_string_list)?.forEach {
                i -> al.add(i)
            };
            al.add(RatActionType.MOVE_TO_BLOCKSTATE.type)
        })

        stack.set(RatDataComponentTypes.blockState, blockState)
        if (blockState.isIn(BlockTags.WOOL)) {
            stack.set(RatDataComponentTypes.color, when (blockState.block) {
                Blocks.WHITE_WOOL      -> DyeColor.WHITE
                Blocks.ORANGE_WOOL     -> DyeColor.ORANGE
                Blocks.MAGENTA_WOOL    -> DyeColor.MAGENTA
                Blocks.LIGHT_BLUE_WOOL -> DyeColor.LIGHT_BLUE
                Blocks.YELLOW_WOOL     -> DyeColor.YELLOW
                Blocks.LIME_WOOL       -> DyeColor.LIME
                Blocks.PINK_WOOL       -> DyeColor.PINK
                Blocks.MAGENTA_WOOL    -> DyeColor.GRAY
                Blocks.LIGHT_GRAY_WOOL -> DyeColor.LIGHT_GRAY
                Blocks.CYAN_WOOL       -> DyeColor.CYAN
                Blocks.PURPLE_WOOL     -> DyeColor.PURPLE
                Blocks.BLUE_WOOL       -> DyeColor.BLUE
                Blocks.BROWN_WOOL      -> DyeColor.BROWN
                Blocks.GREEN_WOOL      -> DyeColor.GREEN
                Blocks.RED_WOOL        -> DyeColor.RED
                else                   -> DyeColor.BLACK
            })
            return ActionResult.SUCCESS
        }
        
        return super.useOnBlock(context)
    }
}
