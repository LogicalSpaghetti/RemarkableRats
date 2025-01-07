package spaghetti.remarkablerats.screen

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import spaghetti.remarkablerats.entity.entities.RatEntity
import spaghetti.remarkablerats.network.EntityIdPayload

class RatEntityScreenHandler(syncId: Int, playerInventory: PlayerInventory, private val ratEntity: RatEntity) :
        ScreenHandler(RatScreenHandlers.ratScreenHandler, syncId) {
//    private val context: ScreenHandlerContext = ScreenHandlerContext.create(ratEntity.world,
//            BlockPos(Vec3i(ratEntity.pos.x.toInt(), ratEntity.pos.y.toInt(), ratEntity.pos.z.toInt())));
    var slotsVisible = true

    // Client Constructor
    constructor(syncId: Int, playerInventory: PlayerInventory, payload: EntityIdPayload) : this(syncId, playerInventory,
            playerInventory.player.world.getEntityById(payload.entityId) as RatEntity)

    // Main Constructor - (Directly called from server)
    init {
        addPlayerInventory(playerInventory)
        addPlayerHotbar(playerInventory)
        addInventory(ratEntity)
        ratEntity.onScreenOpened()
        hideSlots()
    }

    private fun addPlayerInventory(playerInv: PlayerInventory) {
        for (row in 0..2) {
            for (column in 0..8) {
                addSlot(ToggleableSlot(playerInv, 9 + (column + (row * 9)), 8 + (column * 18), 84 + (row * 18)))

            }
        }
    }
    private fun addInventory(inventory: Inventory) {
        for (row in 0..2) {
            for (column in 0..8) {
                addSlot(ToggleableSlot(inventory, (column + (row * 9)), 8 + (column * 18), /* TODO: Find exact value*/16 + (row * 18)))
            }
        }
    }

    private fun addPlayerHotbar(playerInv: PlayerInventory) {
        for (column in 0..8) {
            addSlot(ToggleableSlot(playerInv, column, 8 + (column * 18), 142))
        }
    }

    fun hideSlots() {
        slotsVisible = false
        for (i in 36..< slots.size) { // the first 36 slots are the player's
            (slots[i] as ToggleableSlot).disable()
        }
    }

    fun showSlots() {
        slotsVisible = true
        for (i in 36..< slots.size) { // the first 36 slots are the player's
            (slots[i] as ToggleableSlot).enable()
        }
    }

    fun toggleSlotVisibility() {
        if (slotsVisible) {
            hideSlots()
        } else {
            showSlots()
        }
    }

    // TODO: not implemented
    override fun quickMove(player: PlayerEntity, slot: Int): ItemStack {
        return ItemStack.EMPTY
    }

    override fun canUse(player: PlayerEntity): Boolean {
        return ratEntity.isOwner(player)
    }

    override fun onClosed(player: PlayerEntity?) {
        super.onClosed(player)
        ratEntity.onScreenClosed()
    }
}