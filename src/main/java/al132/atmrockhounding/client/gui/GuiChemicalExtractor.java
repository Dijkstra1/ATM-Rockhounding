package al132.atmrockhounding.client.gui;

import java.util.Arrays;
import java.util.List;

import al132.atmrockhounding.ModConfig;
import al132.atmrockhounding.Reference;
import al132.atmrockhounding.client.container.ContainerChemicalExtractor;
import al132.atmrockhounding.tile.TileChemicalExtractor;
import al132.atmrockhounding.utils.RenderUtils;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiChemicalExtractor extends GuiBase {
    private final InventoryPlayer playerInventory;
    private final TileChemicalExtractor chemicalExtractor;
	public static final int WIDTH = 245;
	public static final int HEIGHT = 256;
	public static final ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guichemicalextractor.png");

	
	
    public GuiChemicalExtractor(InventoryPlayer playerInv, TileChemicalExtractor tile){
        super(tile,new ContainerChemicalExtractor(playerInv, tile));
        this.playerInventory = playerInv;
        this.chemicalExtractor = tile;
        TEXTURE = TEXTURE_REF;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;
	   //fuel
	   if(mouseX >= 11+x && mouseX <= 21+x && mouseY >= 28+y && mouseY <= 78+y){
		   String[] text = {this.chemicalExtractor.powerCount + "/" + this.chemicalExtractor.powerMax + " ticks"};
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   //redstone
	   if(mouseX >= 31+x && mouseX <= 41+x && mouseY >= 28+y && mouseY <= 78+y){
		   String[] text = {this.chemicalExtractor.redstoneCount + "/" + this.chemicalExtractor.redstoneMax + " RF"};
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   
		//syngas tank
		if(mouseX>= 8+x && mouseX <= 24+x && mouseY >= 109+y && mouseY <= 169+y){
			int fluidAmount = 0;
			if(chemicalExtractor.syngasTank.getFluid() != null){
				fluidAmount = this.chemicalExtractor.syngasTank.getFluidAmount();
			}
			String[] text = {fluidAmount + "/" + this.chemicalExtractor.syngasTank.getCapacity() + " mb","Syngas"};
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		
		//fluo tank
		if(mouseX>= 28+x && mouseX <= 44+x && mouseY >= 109+y && mouseY <= 169+y){
			int fluidAmount = 0;
			if(chemicalExtractor.fluoTank.getFluid() != null){
				fluidAmount = this.chemicalExtractor.fluoTank.getFluidAmount();
			}
			String[] text = {fluidAmount + "/" + this.chemicalExtractor.fluoTank.getCapacity() + " mb","Hydrofluoric Acid"};
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
    }

    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
        String device = "Chemical Extractor";
        this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 6, 4210752);
    }

    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //fuel bar
        if (this.chemicalExtractor.powerCount > 0){
	        int k = this.getBarScaled(50, this.chemicalExtractor.powerCount, this.chemicalExtractor.powerMax);
            this.drawTexturedModalRect(i + 11, j + 28 + (50 - k), 246, 0, 10, k);
        }
        //redstone bar
        if (this.chemicalExtractor.redstoneCount > 0){
	        int k = this.getBarScaled(50, this.chemicalExtractor.redstoneCount, this.chemicalExtractor.redstoneMax);
            this.drawTexturedModalRect(i + 31, j + 28 + (50 - k), 246, 109, 10, k);
        }
        //smelt bar
        int k = this.getBarScaled(36, this.chemicalExtractor.cookTime, this.chemicalExtractor.getCookTimeMax());
        this.drawTexturedModalRect(i + 62, j + 97, 250, 50, 6, 36 - k);
		//cabinet bars
		for(int hSlot = 0; hSlot <= 6; hSlot++){
			for(int vSlot = 0; vSlot <= 7; vSlot++){
				int slotID = (hSlot * 8) + vSlot;
		        int cab = this.getBarScaled(19, this.chemicalExtractor.elementList[slotID], ModConfig.factorExtractor);
				if(cab > 19){cab = 19;}
				this.drawTexturedModalRect(i + 100 + (19 * vSlot), j + 19 + (21 * hSlot), 245, 89, 4, 19 - cab);
			}
		}
		
		if(chemicalExtractor.syngasTank.getFluid() != null){
			FluidStack temp = chemicalExtractor.syngasTank.getFluid();
			int capacity = chemicalExtractor.syngasTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 8, j + 109, zLevel, 16, 60);
			}
		}
		
		if(chemicalExtractor.fluoTank.getFluid() != null){
			FluidStack temp = chemicalExtractor.fluoTank.getFluid();
			int capacity = chemicalExtractor.fluoTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 28, j + 109, zLevel, 16, 60);
			}
		}
    }

}