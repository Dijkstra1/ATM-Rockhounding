package al132.atmrockhounding;

import al132.atmrockhounding.blocks.ModBlocks;
import al132.atmrockhounding.client.GuiHandler;
import al132.atmrockhounding.compat.crafttweaker.CTPlugin;
import al132.atmrockhounding.compat.crafttweaker.utils.TweakerPlugin;
import al132.atmrockhounding.fluids.ModFluids;
import al132.atmrockhounding.items.ModItems;
import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.world.ChemOresGenerator;
import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e){
		// Load Config
		ModConfig.loadConfig(e);

		// Register Contents
		ModBlocks.init();
		ModBlocks.register();
		ModItems.init();
		ModFluids.registerFluidContainers();
	}

	public void init(FMLInitializationEvent e) {
		TweakerPlugin.register("atmrockhounding",CTPlugin.class);

		GameRegistry.registerWorldGenerator(new ChemOresGenerator(), 1);

		ModRecipes.init();

		NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MODID, new GuiHandler());
	}

	public void postInit(FMLPostInitializationEvent e){}

	public void registerTileEntitySpecialRenderer() {}

	public void registerRenderInformation() {}
	
	public void initFluidModel(Block block, Fluid fluid) {}
}
