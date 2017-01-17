package al132.atmrockhounding.client.gui;

import java.util.Arrays;
import java.util.List;

import al132.atmrockhounding.ModConfig;
import al132.atmrockhounding.Reference;
import al132.atmrockhounding.client.container.ContainerMineralAnalyzer;
import al132.atmrockhounding.tile.TileMineralAnalyzer;
import al132.atmrockhounding.utils.RenderUtils;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMineralAnalyzer extends GuiBase {
	
    private final InventoryPlayer playerInventory;
    private final TileMineralAnalyzer mineralAnalyzer;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 191;
	public static final ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guimineralanalyzer.png");

    public GuiMineralAnalyzer(InventoryPlayer playerInv, TileMineralAnalyzer tile){
        super(tile, new ContainerMineralAnalyzer(playerInv, tile));
        this.playerInventory = playerInv;
        TEXTURE = TEXTURE_REF;

        this.mineralAnalyzer = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;
	   //bars progression (fuel-redstone)
	   if(mouseX >= 9+x && mouseX <= 19+x && mouseY >= 54+y && mouseY <= 104+y){
		   String[] text = {this.mineralAnalyzer.getEnergyStorage().getEnergyStored() + "/" + this.mineralAnalyzer.getEnergyStorage().getMaxEnergyStored() + " Energy"};
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   
		//sulf tank
		if(mouseX>= 105+x && mouseX <= 121+x && mouseY >= 36+y && mouseY <= 96+y){
			int fluidAmount = 0;
			if(mineralAnalyzer.sulfTank.getFluid() != null){
				fluidAmount = this.mineralAnalyzer.sulfTank.getFluidAmount();
			}
			String[] text = {fluidAmount + "/" + this.mineralAnalyzer.sulfTank.getCapacity() + " mb","Sulfuric Acid"};
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		
		//chlo tank
		if(mouseX>= 127+x && mouseX <= 143+x && mouseY >= 36+y && mouseY <= 96+y){
			int fluidAmount = 0;
			if(mineralAnalyzer.chloTank.getFluid() != null){
				fluidAmount = this.mineralAnalyzer.chloTank.getFluidAmount();
			}
			String[] text = {fluidAmount + "/" + this.mineralAnalyzer.chloTank.getCapacity() + " mb", "Hydrochloric Acid"};
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		
		//fluo tank
		if(mouseX>= 149+x && mouseX <= 165+x && mouseY >= 36+y && mouseY <= 96+y){
			int fluidAmount = 0;
			if(mineralAnalyzer.fluoTank.getFluid() != null){
				fluidAmount = this.mineralAnalyzer.fluoTank.getFluidAmount();
			}
			String[] text = {fluidAmount + "/" + this.mineralAnalyzer.fluoTank.getCapacity() + " mb","Hydrofluoric Acid"};
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
        String device = "Mineral Analyzer";
        this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 6, 4210752);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
       super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //power bar
        if (this.mineralAnalyzer.getEnergyStorage().getEnergyStored() > 0){
            int k = this.getBarScaled(50, this.mineralAnalyzer.getEnergyStorage().getEnergyStored(), this.mineralAnalyzer.getEnergyStorage().getMaxEnergyStored());
            this.drawTexturedModalRect(i + 9, j + 54 + (50 - k), 176, 51, 10, k);
        }
        //smelt bar
        if (this.mineralAnalyzer.cookTime > 0){
            int k = this.getBarScaled(51, this.mineralAnalyzer.cookTime, ModConfig.speedAnalyzer);
            this.drawTexturedModalRect(i + 28, j + 42, 176, 0, 27, k);
        }
        

		if(mineralAnalyzer.sulfTank.getFluid() != null){
			FluidStack temp = mineralAnalyzer.sulfTank.getFluid();
			int capacity = mineralAnalyzer.sulfTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 105, j + 36, zLevel, 16, 60);
			}
		}
		
		if(mineralAnalyzer.chloTank.getFluid() != null){
			FluidStack temp = mineralAnalyzer.chloTank.getFluid();
			int capacity = mineralAnalyzer.chloTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 127, j + 36, zLevel, 16, 60);
			}
		}
		
		if(mineralAnalyzer.fluoTank.getFluid() != null){
			FluidStack temp = mineralAnalyzer.fluoTank.getFluid();
			int capacity = mineralAnalyzer.fluoTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 149, j + 36, zLevel, 16, 60);
			}
		}
		
		
    }

}