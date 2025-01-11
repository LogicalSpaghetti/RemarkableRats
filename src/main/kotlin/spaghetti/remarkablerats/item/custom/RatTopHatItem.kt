package spaghetti.remarkablerats.item.custom

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

class RatTopHatItem(settings: Settings) : Item(settings) {

    override fun appendTooltip(stack: ItemStack, context: TooltipContext, tooltip: MutableList<Text>,
            type: TooltipType?) {
        super.appendTooltip(stack, context, tooltip, type)
        tooltip.add(Text.literal("${stack.get(RatDataComponentTypes.color)}"))
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
        val itemStack = context.stack
        val blockState = context.world.getBlockState(context.blockPos)

        if (blockState.isIn(BlockTags.WOOL)) {
            itemStack.set(RatDataComponentTypes.color, DyeColor.RED)
        } else if (blockState.isIn(BlockTags.TERRACOTTA)) {
            itemStack.set(RatDataComponentTypes.color, DyeColor.BLUE)
        } else {
            itemStack.set(RatDataComponentTypes.color, DyeColor.GREEN)
        }

        return super.useOnBlock(context)
    }
}
