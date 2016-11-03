package al132.atmrockhounding;

import al132.atmrockhounding.blocks.ModBlocks;
import al132.atmrockhounding.client.GuiHandler;
import al132.atmrockhounding.items.ModItems;
import al132.atmrockhounding.recipes.ModArray;
import al132.atmrockhounding.recipes.ModDictionary;
import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.world.ChemOresGenerator;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e){
		// Load Config
		ModConfig.loadConfig(e);

		// Load Arrays
		ModArray.loadArray();

		// Register Contents
		ModBlocks.init();
		ModBlocks.register();
		ModItems.init();

		// Register oreDictionary
		ModDictionary.loadDictionary();
	}

	public void init(FMLInitializationEvent e) {

		GameRegistry.registerWorldGenerator(new ChemOresGenerator(), 1);

		ModRecipes.init();

		NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MODID, new GuiHandler());
	}

	public void postInit(FMLPostInitializationEvent e){}

	public void registerTileEntitySpecialRenderer() {}

	public void registerRenderInformation() {}
}
