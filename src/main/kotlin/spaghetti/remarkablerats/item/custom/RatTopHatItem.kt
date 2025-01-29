package spaghetti.remarkablerats.item.custom

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.DyeColor
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
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

        addEntryToStackList(stack, RatActionType.TELEPORT_TO_BLOCKSTATE, data)

        return ActionResult.SUCCESS
    }

    private fun addEntryToStackList(stack: ItemStack, actionType: RatActionType, data: Int) {
        stack.set(RatDataComponentTypes.rat_action_int_list, ArrayList<Int>().also { al ->
            stack.get(RatDataComponentTypes.rat_action_int_list)?.forEach { i ->
                al.add(i)
            };
            al.add(data)
        })

        stack.set(RatDataComponentTypes.rat_action_string_list, ArrayList<String>().also {
            al -> stack.get(RatDataComponentTypes.rat_action_string_list)?.forEach {
            i -> al.add(i)
            };
            al.add(actionType.type)
        })

        // probably works, TODO: test
//        stack.set(rat_action_string_list, listOf(*stack.get(rat_action_string_list)?.toTypedArray().orEmpty(), actionType.type))
    }

    override fun canMine(state: BlockState, world: World, pos: BlockPos, miner: PlayerEntity): Boolean {
        if (!world.isClient) blockPunched(miner, state, world, pos, miner.getStackInHand(Hand.MAIN_HAND))
        return false
    }

    private fun blockPunched(player: PlayerEntity, state: BlockState, world: World, pos: BlockPos, stackInHand: ItemStack) {
        addEntryToStackList(stackInHand, RatActionType.PLACE_BLOCK, Direction.UP.id)
    }
}
