package al132.atmrockhounding.client.gui;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Range;

import al132.atmrockhounding.Reference;
import al132.atmrockhounding.client.container.ContainerLabOven;
import al132.atmrockhounding.tile.TileLabOven;
import al132.atmrockhounding.utils.RenderUtils;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLabOven extends GuiBase {
	private final InventoryPlayer playerInventory;
	private final TileLabOven labOven;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 215;
	public static final ResourceLocation TEXTURE_REF =  new ResourceLocation(Reference.MODID + ":textures/gui/guilaboven.png");

	public GuiLabOven(InventoryPlayer playerInv, TileLabOven tile){
		super(tile,new ContainerLabOven(playerInv, tile));
		this.playerInventory = playerInv;
		TEXTURE = TEXTURE_REF;
		this.labOven = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;


	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		//fuel
		if(mouseX >= 11+x && mouseX <= 21+x && mouseY >= 74+y && mouseY <= 124+y){
			String[] text = {this.labOven.getPower() + "/" + this.labOven.getPowerMax() + " ticks"};
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		//redstone
		if(mouseX >= 29+x && mouseX <= 38+x && mouseY >= 26+y && mouseY <= 75+y){
			String[] text = {this.labOven.getRedstone() + "/" + this.labOven.getRedstoneMax() + " RF"};
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}

		//input tank
		if(mouseX>= 146+x && mouseX <= 166+x && mouseY >= 45+y && mouseY <= 110+y){
			int fluidAmount = 0;
			if(labOven.inputTank.getFluid() != null){
				fluidAmount = this.labOven.inputTank.getFluidAmount();
			}
			String[] text = {fluidAmount + "/" + this.labOven.inputTank.getCapacity() + " mb "};
			if(labOven.inputTank.getFluid() != null) text[0] += labOven.inputTank.getFluid().getLocalizedName();
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		
		//output tank
		if(mouseX>= 89+x && mouseX <= 109+x && mouseY >= 45+y && mouseY <= 110+y){
			int fluidAmount = 0;
			if(labOven.outputTank.getFluid() != null){
				fluidAmount = this.labOven.outputTank.getFluidAmount();
			}
			String[] text = {fluidAmount + "/" + this.labOven.outputTank.getCapacity() + " mb "};
			if(labOven.outputTank.getFluid() != null) text[0] += labOven.outputTank.getFluid().getLocalizedName();
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
	}

	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		String device = "Lab Oven";
		this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 6, 4210752);

	}

	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		//power bar
		if (this.labOven.powerCount > 0){
			int k = this.getBarScaled(50, this.labOven.powerCount, this.labOven.powerMax);
			this.drawTexturedModalRect(i + 11, j + 74 + (50 - k), 176, 27, 10, k);
		}

		//redstone
		if (this.labOven.redstoneCount > 0){
			int k = this.getBarScaled(50, this.labOven.redstoneCount, this.labOven.redstoneMax);
			this.drawTexturedModalRect(i + 29, j + 26 + (50 - k), 176, 81, 10, k);
		}
		//smelt bar
		int k = this.getBarScaled(14, this.labOven.cookTime, this.labOven.getCookTimeMax());
		this.drawTexturedModalRect(i + 67, j + 70, 176, 0, k, 15); //bubbles
		//process icons
		if(this.labOven.canSynthesize()){
			this.drawTexturedModalRect(i + 93, j + 114, 176, 131, 12, 14); //fire
			this.drawTexturedModalRect(i + 121, j + 72, 176, 145, 14, 9); //fluid drop
		}
		//FluidStack stack = new FluidStack(FluidRegistry.getFluid(labOven.inputTankFluidID),labOven.inputTankQuantity);

		if(labOven.inputTank.getFluid() != null){
			FluidStack temp = labOven.inputTank.getFluid();
			int capacity = labOven.inputTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 146, j + 45, zLevel, 20, 65);
			}
		}

		if(labOven.outputTank.getFluid() != null){
			FluidStack temp = labOven.outputTank.getFluid();
			int capacity = labOven.outputTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 89, j + 45, zLevel, 20, 65);
			}
		}
	}

}