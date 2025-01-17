package spaghetti.remarkablerats.screen

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.gui.tooltip.Tooltip
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import spaghetti.remarkablerats.id

class RatEntityScreen(handler: RatEntityScreenHandler, inventory: PlayerInventory, title: Text?) :
        HandledScreen<RatEntityScreenHandler?>(handler, inventory, title) {

    private val inventoryButton: ButtonWidget = ButtonWidget.builder(Text.translatable("screen.remarkablerats.open_inventory")
    ) { button ->
        run {
            // code here is run when the button is pressed!
            spaghetti.remarkablerats.logger.info("Button $button Pressed")
            handler.toggleSlotVisibility()
        }
    }
            .size(100, 20)
            .tooltip(Tooltip.of(Text.translatable("screen.remarkablerats.open_inventory")))
            .build()

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

    override fun init() {
        super.init()
        inventoryButton.setPosition(this.x, this.y - 20)
        // This code is injected into the start of MinecraftServer.loadWorld()V
        addDrawableChild(inventoryButton)
    }

    companion object {
        private val texture: Identifier = id("textures/gui/generic_15.png")
    }
}
