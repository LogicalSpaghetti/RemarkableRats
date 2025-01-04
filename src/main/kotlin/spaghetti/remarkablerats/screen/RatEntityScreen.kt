package spaghetti.remarkablerats.screen

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import spaghetti.remarkablerats.id

class RatEntityScreen(handler: RatEntityScreenHandler?, inventory: PlayerInventory, title: Text?) :
        HandledScreen<RatEntityScreenHandler?>(handler, inventory, title) {
    override fun drawBackground(context: DrawContext, delta: Float, mouseX: Int, mouseY: Int) {
        context.drawTexture(texture, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight)

        //        int energyBarSize = MathHelper.ceil(this.handler.getEnergyPercent() * 66);
//        context.fill(this.x + 144, this.y + 10 + 66 - energyBarSize, this.x + 144 + 20, this.y + 10 + 66, 0xFFD4AF37);
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)
        drawMouseoverTooltip(context, mouseX, mouseY)

        //        int energyBarSize = MathHelper.ceil(this.handler.getEnergyPercent() * 66);
//        if (isPointWithinBounds(144, 10 + 66 - energyBarSize, 20, energyBarSize, mouseX, mouseY)) {
//            context.drawTooltip(this.textRenderer, Text.literal(this.handler.getEnergy() + " / " + this.handler.getMaxEnergy() + " FE"), mouseX, mouseY);
//        }
    }

    companion object {
        private val texture: Identifier = id("textures/gui/single_slot_gui.png")
    }
}
