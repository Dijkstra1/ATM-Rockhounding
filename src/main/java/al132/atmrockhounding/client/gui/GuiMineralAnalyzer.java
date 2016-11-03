package al132.atmrockhounding.client.gui;

import java.util.Arrays;
import java.util.List;

import al132.atmrockhounding.ModConfig;
import al132.atmrockhounding.Reference;
import al132.atmrockhounding.client.container.ContainerMineralAnalyzer;
import al132.atmrockhounding.tile.TileMineralAnalyzer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
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
	   if(mouseX >= 11+x && mouseX <= 20+x && mouseY >= 40+y && mouseY <= 89+y){
		   String[] text = {this.mineralAnalyzer.powerCount + "/" + this.mineralAnalyzer.powerMax + " ticks"};
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
        String device = "Mineral Analyzer";
        this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
       super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //power bar
        if (this.mineralAnalyzer.powerCount > 0){
            int k = this.getBarScaled(50, this.mineralAnalyzer.powerCount, this.mineralAnalyzer.powerMax);
            this.drawTexturedModalRect(i + 11, j + 40 + (50 - k), 176, 51, 10, k);
        }
        //smelt bar
        if (this.mineralAnalyzer.cookTime > 0){
            int k = this.getBarScaled(51, this.mineralAnalyzer.cookTime, ModConfig.speedAnalyzer);
            this.drawTexturedModalRect(i + 48, j + 42, 176, 0, 27, k);
        }
    }

}