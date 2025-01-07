package spaghetti.remarkablerats.screen

import net.minecraft.inventory.Inventory
import net.minecraft.screen.slot.Slot

internal class ToggleableSlot(inventory: Inventory?, index: Int, x: Int, y: Int) :
        Slot(inventory, index, x, y) {
    private var isEnabled = true

    override fun isEnabled(): Boolean = isEnabled

    fun enable() {
        isEnabled = true
    }

    fun disable() {
        isEnabled = false
    }

    fun toggle() {
        isEnabled = isEnabled xor true
    }
}
