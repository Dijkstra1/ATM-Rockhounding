package al132.atmrockhounding.client;

import al132.atmrockhounding.client.container.ContainerChemicalExtractor;
import al132.atmrockhounding.client.container.ContainerLabOven;
import al132.atmrockhounding.client.container.ContainerMetalAlloyer;
import al132.atmrockhounding.client.container.ContainerMineralAnalyzer;
import al132.atmrockhounding.client.container.ContainerMineralSizer;
import al132.atmrockhounding.client.gui.GuiChemicalExtractor;
import al132.atmrockhounding.client.gui.GuiLabOven;
import al132.atmrockhounding.client.gui.GuiMetalAlloyer;
import al132.atmrockhounding.client.gui.GuiMineralAnalyzer;
import al132.atmrockhounding.client.gui.GuiMineralSizer;
import al132.atmrockhounding.tile.TileChemicalExtractor;
import al132.atmrockhounding.tile.TileLabOven;
import al132.atmrockhounding.tile.TileMetalAlloyer;
import al132.atmrockhounding.tile.TileMineralAnalyzer;
import al132.atmrockhounding.tile.TileMineralSizer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int NO_GUI = -1;
	public static final int labOvenID = 0;
	public static final int mineralSizerID = 1;
	public static final int mineralAnalyzerID = 2;
	public static final int chemicalExtractorID = 3;
	public static final int crawlerAssemblerID = 4;
	public static final int metalAlloyerID = 5;
	public static final int owcAssemblerID = 6;
	public static final int owcControllerID = 7;
	public static final int petrographerTableID = 8;


	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(new BlockPos(x,y,z));
		switch(ID) {
		default: return null;
		case labOvenID:
			if (entity != null && entity instanceof TileLabOven){return new ContainerLabOven(player.inventory, (TileLabOven) entity);}
		case mineralSizerID:
			if (entity != null && entity instanceof TileMineralSizer){return new ContainerMineralSizer(player.inventory, (TileMineralSizer) entity);}
		case mineralAnalyzerID:
			if (entity != null && entity instanceof TileMineralAnalyzer){return new ContainerMineralAnalyzer(player.inventory, (TileMineralAnalyzer) entity);}
		case chemicalExtractorID:
			if (entity != null && entity instanceof TileChemicalExtractor){return new ContainerChemicalExtractor(player.inventory, (TileChemicalExtractor) entity);}
		case metalAlloyerID:
			if (entity != null && entity instanceof TileMetalAlloyer){return new ContainerMetalAlloyer(player.inventory, (TileMetalAlloyer) entity);}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(new BlockPos(x,y,z));
		switch(ID) {
		default: return null;
		case labOvenID:
			if (entity != null && entity instanceof TileLabOven) {return new GuiLabOven(player.inventory, (TileLabOven) entity);}
		case mineralSizerID:
			if (entity != null && entity instanceof TileMineralSizer) {return new GuiMineralSizer(player.inventory, (TileMineralSizer) entity);}
		case mineralAnalyzerID:
			if (entity != null && entity instanceof TileMineralAnalyzer) {return new GuiMineralAnalyzer(player.inventory, (TileMineralAnalyzer) entity);}
		case chemicalExtractorID:
			if (entity != null && entity instanceof TileChemicalExtractor) {return new GuiChemicalExtractor(player.inventory, (TileChemicalExtractor) entity);}
		case metalAlloyerID:
			if (entity != null && entity instanceof TileMetalAlloyer) {return new GuiMetalAlloyer(player.inventory, (TileMetalAlloyer) entity);}
		}
		return null;
	}

}
