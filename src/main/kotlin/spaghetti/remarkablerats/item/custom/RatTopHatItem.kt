package spaghetti.remarkablerats.item.custom

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.registry.tag.BlockTags
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.DyeColor
import spaghetti.remarkablerats.data.RatDataComponentTypes

class RatTopHatItem(settings: Settings) : Item(settings) {

    override fun appendTooltip(stack: ItemStack, context: TooltipContext, tooltip: MutableList<Text>,
            type: TooltipType?) {
        super.appendTooltip(stack, context, tooltip, type)
        tooltip.add(Text.literal("${stack.get(RatDataComponentTypes.color)}"))
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
