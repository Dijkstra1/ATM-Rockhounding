package al132.atmrockhounding.blocks;

import al132.atmrockhounding.enums.EnumAlloy;
import al132.atmrockhounding.enums.EnumAlloy2;
import al132.atmrockhounding.enums.EnumMineral;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static Block mineralOres;
	public static AlloyBlocks alloyBlocks;
	public static AlloyBlocks2 alloyBlocks2;
	public static AlloyBricks alloyBricks;
	public static AlloyBricks2 alloyBricks2;

	public static Block labOven;
	public static Block mineralSizer;
	public static Block mineralAnalyzer;
	public static Block metalAlloyer;
	public static Block chemicalExtractor;
	public static Block saltMaker;

	//initialize the block
	public static void init() {
		//blocks

		mineralOres = new MineralOres(3.0F, 5.0F, "mineralOres");
		alloyBlocks = new AlloyBlocks(Material.IRON, EnumAlloy.getLowerNameArray(), 3.0F, 5.0F, "alloyBlocks", SoundType.METAL);
		alloyBlocks2 = new AlloyBlocks2(Material.IRON, EnumAlloy2.getLowerNameArray(), 3.0F, 5.0F, "alloyBlocks2", SoundType.METAL);
		
		alloyBricks = new AlloyBricks(Material.IRON, EnumAlloy.getLowerNameArray(), 3.0F, 5.0F, "alloyBricks", SoundType.METAL);
		alloyBricks2 = new AlloyBricks2(Material.IRON, EnumAlloy2.getLowerNameArray(), 3.0F, 5.0F, "alloyBricks2", SoundType.METAL);
		
		labOven = new LabOven(3.0F, 5.0F, "labOven");
		mineralSizer = new MineralSizer(3.0F, 5.0F, "mineralSizer");
		mineralAnalyzer = new MineralAnalyzer(3.0F, 5.0F, "mineralAnalyzer");
		chemicalExtractor = new ChemicalExtractor(3.0F, 5.0F, "chemicalExtractor");
		metalAlloyer = new MetalAlloyer(3.0F, 5.0F, "metalAlloyer");

		//saltMaker = new SaltMaker(3.0F, 5.0F, "saltMaker");
	}

	//recall the registry
	public static void register() {
		//blocks
		registerMetaBlock(mineralOres, "mineralOres");
		registerMetaBlock(alloyBlocks, "alloyBlocks");
		registerMetaBlock(alloyBlocks2, "alloyBlocks2");
		registerMetaBlock(alloyBricks, "alloyBricks");
		registerMetaBlock(alloyBricks2, "alloyBricks2");
		//registerMetaBlock(saltMaker, "saltMaker");
	}

	//register blocks and itemblocks
	public static void registerMetaBlock(Block block, String name) {
		GameRegistry.register(block);
		GameRegistry.register(new TiersIB(block).setRegistryName(name));
	}

	//register simple blocks and itemblocks
	public static void registerSimpleBlock(Block block, String name) {
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block).setRegistryName(name));
	}

	public static void initClient(){
		//blocks
		for(int i = 0; i < EnumMineral.size(); i++){
			registerMetaBlockRender(mineralOres, i, EnumMineral.getName(i));
		}
		//TODO for(int i = 0; i < ModArray.saltMakerArray.length; i++){		registerMetaBlockRender(saltMaker, i, ModArray.saltMakerArray[i]);				}
		for(int i = 0; i < EnumAlloy.size(); i++){
			registerMetaBlockRender(alloyBlocks, i, EnumAlloy.getName(i));
			registerMetaBlockRender(alloyBricks, i, EnumAlloy.getName(i));
		}
		for(int i = 0; i < EnumAlloy2.size(); i++){
			registerMetaBlockRender(alloyBlocks2, i, EnumAlloy2.getName(i));
			registerMetaBlockRender(alloyBricks2, i, EnumAlloy2.getName(i));
		}

		registerSingleBlockRender(labOven, 0, "labOven");
		registerSingleBlockRender(mineralSizer, 0, "mineralSizer");
		registerSingleBlockRender(mineralAnalyzer, 0, "mineralAnalyzer");
		registerSingleBlockRender(chemicalExtractor, 0, "chemicalExtractor");
		registerSingleBlockRender(metalAlloyer, 0, "metalAlloyer");
	}


	//render meta block
	public static void registerMetaBlockRender(Block block, int meta, String fileName){
		Item item = Item.getItemFromBlock(block);
		ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName() + "_" + fileName, "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, model );
	}
	//render single block
	public static void registerSingleBlockRender(Block block, int meta, String fileName){
		Item item = Item.getItemFromBlock(block);
		ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName(), "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, model );
	}

}
