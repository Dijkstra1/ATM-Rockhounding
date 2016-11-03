package al132.atmrockhounding.client.gui;

import java.util.Arrays;
import java.util.List;

import al132.atmrockhounding.Reference;
import al132.atmrockhounding.client.container.ContainerMetalAlloyer;
import al132.atmrockhounding.tile.TileMetalAlloyer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMetalAlloyer extends GuiBase {
	private final InventoryPlayer playerInventory;
	private final TileMetalAlloyer metalAlloyer;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 201;
	public static final ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guimetalalloyer.png");

	public GuiMetalAlloyer(InventoryPlayer playerInv, TileMetalAlloyer tile){
		super(tile,new ContainerMetalAlloyer(playerInv, tile));
		this.playerInventory = playerInv;
		TEXTURE = TEXTURE_REF;
		this.metalAlloyer = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		   //bars progression (fuel)
		   if(mouseX >= 11+x && mouseX <= 20+x && mouseY >= 40+y && mouseY <= 89+y){
			   String[] text = {this.metalAlloyer.getPower() + "/" + this.metalAlloyer.getPowerMax() + " ticks"};
			   List<String> tooltip = Arrays.asList(text);
			   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		   }
	}

	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);

    	String device = "Metal Alloyer";
        this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 6, 4210752);

        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        //power bar
        if (this.metalAlloyer.powerCount > 0){
            int k = this.getBarScaled(50, this.metalAlloyer.powerCount, this.metalAlloyer.powerMax);
            this.drawTexturedModalRect(i + 11, j + 40 + (50 - k), 176, 27, 10, k);
        }
        //smelt bar
        if (this.metalAlloyer.cookTime > 0){
            int k = this.getBarScaled(17, this.metalAlloyer.cookTime, this.metalAlloyer.getCookTimeMax());
            this.drawTexturedModalRect(i + 99, j + 69, 176, 0, 14, k);
        }
	}
}