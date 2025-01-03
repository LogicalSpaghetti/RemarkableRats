package spaghetti.remarkablerats.item.custom

import net.minecraft.advancement.criterion.Criteria
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.NbtComponent
import net.minecraft.entity.EntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.Fluid
import net.minecraft.item.EntityBucketItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundEvent
import net.minecraft.stat.Stats
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.RaycastContext
import net.minecraft.world.World

class BundleOfRatsItem(type: EntityType<*>?, fluid: Fluid?, emptyingSound: SoundEvent?, settings: Settings?) :
        EntityBucketItem(type, fluid, emptyingSound, settings) {
    override fun placeFluid(player: PlayerEntity?, world: World, pos: BlockPos, hitResult: BlockHitResult?): Boolean =
            true

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val itemStack = user.getStackInHand(hand)
        val blockHitResult = raycast(world, user, RaycastContext.FluidHandling.NONE)

        // TODO: HitResult.Type.ENTITY, quick-deploy attack rat?
        // if a block was hit
        if (blockHitResult.type == HitResult.Type.BLOCK) {
            val blockPos = blockHitResult.blockPos
            val direction = blockHitResult.side
            val blockPos2 = blockPos.offset(direction)

            this.onEmptied(user, world, itemStack, blockPos2)
            if (user is ServerPlayerEntity) {
                Criteria.PLACED_BLOCK.trigger(user, blockPos2, itemStack)
            }
            user.incrementStat(Stats.USED.getOrCreateStat(this))
            return TypedActionResult.success(getEmptiedStack(itemStack, user), world.isClient())
        }
        return TypedActionResult.pass(itemStack)
    }

    override fun appendTooltip(stack: ItemStack, context: TooltipContext, tooltip: MutableList<Text>,
            type: TooltipType) {
        super.appendTooltip(stack, context, tooltip, type)
        val nbt = stack.getOrDefault(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT).nbt
        tooltip.add(Text.literal("Variant: ${nbt.getInt("Variant")}"))
        if (nbt.contains("OwnerUuid") && nbt.contains("OwnerName")) {
            tooltip.add(Text.literal("Tamed By: ${nbt.getString("OwnerName")}"))
        }
    }

    companion object {
        fun getEmptiedStack(stack: ItemStack, player: PlayerEntity): ItemStack {
            if (player.abilities.creativeMode) {
                return stack
            }
            return ItemStack(Items.BUNDLE)
        }
    }
}
