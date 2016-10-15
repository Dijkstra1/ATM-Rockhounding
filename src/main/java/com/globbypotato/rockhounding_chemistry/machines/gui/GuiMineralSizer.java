package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralSizer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMineralSizer extends GuiContainer {
	
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/guimineralsizer.png");
    private final InventoryPlayer playerInventory;
    private final TileEntityMineralSizer mineralSizer;

    public GuiMineralSizer(InventoryPlayer playerInv, TileEntityMineralSizer furnaceInv){
        super(new ContainerMineralSizer(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.mineralSizer = furnaceInv;
		this.xSize = 176;
		this.ySize = 191;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;
	   //recipe buttons
	   if(mouseX >= 52+x && mouseX <= 67+x && mouseY >= 16+y && mouseY <= 31+y){
		   String[] text = {"Previous Recipe"};
	       List tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   if(mouseX >= 88+x && mouseX <= 103+x && mouseY >= 16+y && mouseY <= 31+y){
		   String[] text = {"Next Recipe"};
	       List tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   //bars progression (fuel-redstone)
	   if(mouseX >= 11+x && mouseX <= 20+x && mouseY >= 40+y && mouseY <= 89+y){
		   String[] text = {this.mineralSizer.getField(0) + "/" + this.mineralSizer.getField(1) + " ticks"};
	       List tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }

    }

    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        String s = this.mineralSizer.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //power bar
        if (this.mineralSizer.powerCount > 0){
            int k = this.getPowerLeftScaled(50);
            this.drawTexturedModalRect(i + 11, j + 40 + (50 - k), 176, 51, 10, k);
        }
        //smelt bar
        int l = this.getCookProgressScaled(46);
        this.drawTexturedModalRect(i + 66, j + 34, 176, 0, l, 46);
    }

    private int getCookProgressScaled(int pixels){
        int i = this.mineralSizer.getField(2);
        int j = this.mineralSizer.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    private int getPowerLeftScaled(int pixels){
        int i = this.mineralSizer.getField(1);
        if (i == 0){i = this.mineralSizer.machineSpeed();}
        return this.mineralSizer.getField(0) * pixels / i;
    }

}