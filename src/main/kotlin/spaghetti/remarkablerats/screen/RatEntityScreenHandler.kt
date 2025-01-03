package spaghetti.remarkablerats.screen

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.slot.Slot
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3i
import spaghetti.remarkablerats.entity.entities.RatEntity
import spaghetti.remarkablerats.network.EntityIdPayload

class RatEntityScreenHandler(syncId: Int, playerInventory: PlayerInventory, private val ratEntity: RatEntity) :
        ScreenHandler(RatScreenHandlers.ratScreenHandler, syncId) {
    private val context: ScreenHandlerContext = ScreenHandlerContext.create(ratEntity.world,
            BlockPos(Vec3i(ratEntity.pos.x.toInt(), ratEntity.pos.y.toInt(), ratEntity.pos.z.toInt())));

    // Client Constructor
    constructor(syncId: Int, playerInventory: PlayerInventory, payload: EntityIdPayload) : this(syncId, playerInventory,
            playerInventory.player.world.getEntityById(payload.entityId) as RatEntity)

    // Main Constructor - (Directly called from server)
    init {
        addPlayerInventory(playerInventory)
        addPlayerHotbar(playerInventory)
    }

    private fun addPlayerInventory(playerInv: PlayerInventory) {
        for (row in 0..2) {
            for (column in 0..8) {
                addSlot(Slot(playerInv, 9 + (column + (row * 9)), 8 + (column * 18), 84 + (row * 18)))
            }
        }
    }

    private fun addPlayerHotbar(playerInv: PlayerInventory) {
        for (column in 0..8) {
            addSlot(Slot(playerInv, column, 8 + (column * 18), 142))
        }
    }

    // TODO: not implemented
    override fun quickMove(player: PlayerEntity, slot: Int): ItemStack {
        return ItemStack.EMPTY
    }

    override fun canUse(player: PlayerEntity): Boolean {
        return ratEntity.isOwner(player)
    }
}