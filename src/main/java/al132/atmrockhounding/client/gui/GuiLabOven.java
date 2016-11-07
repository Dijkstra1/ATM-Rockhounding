package al132.atmrockhounding.client.gui;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;
import al132.atmrockhounding.Reference;
import al132.atmrockhounding.client.container.ContainerLabOven;
import al132.atmrockhounding.tile.TileLabOven;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLabOven extends GuiBase {
	private final InventoryPlayer playerInventory;
	private final TileLabOven labOven;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 215;

	public GuiLabOven(InventoryPlayer playerInv, TileLabOven tile){
		super(tile,new ContainerLabOven(playerInv, tile));
		this.playerInventory = playerInv;
		TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/guilaboven.png");
		this.labOven = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		//bars progression (fuel-redstone)
		if(mouseX >= 11+x && mouseX <= 20+x && mouseY >= 40+y && mouseY <= 89+y){
			String[] text = {this.labOven.getPower() + "/" + this.labOven.getPowerMax() + " ticks"};
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		if(mouseX >= 155+x && mouseX <= 164+x && mouseY >= 40+y && mouseY <= 89+y){
			String[] text = {this.labOven.getRedstone() + "/" + this.labOven.getRedstoneMax() + " RF"};
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
	}

	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		String device = "Lab Oven";
		this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 6, 4210752);

		this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
		if(this.labOven.getInputTank().getFluid() != null){
		ResourceLocation fluidLocation = this.labOven.getInputTank().getFluid().getFluid().getStill();
		TextureAtlasSprite temp = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(fluidLocation.toString());
		this.drawTexturedModalRect(5, 5, temp, 10, 100);
	}
	
	}

	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		//power bar
		if (this.labOven.powerCount > 0){
			int k = this.getBarScaled(50, this.labOven.powerCount, this.labOven.powerMax);
			this.drawTexturedModalRect(i + 11, j + 40 + (50 - k), 176, 27, 10, k);
		}

		
		//redstone
		if (this.labOven.redstoneCount > 0){
			int k = this.getBarScaled(50, this.labOven.redstoneCount, this.labOven.redstoneMax);
			this.drawTexturedModalRect(i + 155, j + 40 + (50 - k), 176, 81, 10, k);
		}
		//smelt bar
		int k = this.getBarScaled(14, this.labOven.cookTime, this.labOven.getCookTimeMax());
		this.drawTexturedModalRect(i + 61, j + 58, 176, 0, k, 15);
		//process icons
		if(this.labOven.isBurning()){
			this.drawTexturedModalRect(i + 82, j + 79, 176, 131, 12, 14);
			this.drawTexturedModalRect(i + 100, j + 63, 176, 145, 14, 9);
		}
	}

}