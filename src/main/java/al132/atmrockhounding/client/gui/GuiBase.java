package al132.atmrockhounding.client.gui;

import al132.atmrockhounding.client.container.ContainerBase;
import al132.atmrockhounding.tile.TileBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public abstract class GuiBase extends GuiContainer{
	
    protected static ResourceLocation TEXTURE = new ResourceLocation("");
    
	public GuiBase(TileBase tile, ContainerBase container) {
        super(container);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	    this.mc.getTextureManager().bindTexture(TEXTURE);
	    this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	protected int getBarScaled(int pixels, int count, int max) {
        return count > 0 && max > 0 ? count * pixels / max : 0;
	}

}