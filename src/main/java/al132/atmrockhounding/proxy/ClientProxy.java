package al132.atmrockhounding.proxy;

import al132.atmrockhounding.CommonProxy;
import al132.atmrockhounding.blocks.ModBlocks;
import al132.atmrockhounding.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e){
		super.preInit(e);
		ModBlocks.initClient();
		ModItems.initClient();
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}
	
	@Override
	public void initFluidModel(Block block, Fluid fluid) {
		//ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		final ModelResourceLocation fluidLocation = new ModelResourceLocation(
				"atmrockhounding:textures/blocks" + block.getUnlocalizedName());

		// use a custom state mapper which will ignore the LEVEL property
		ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return fluidLocation;
			}
		});
	}
}