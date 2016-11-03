package al132.atmrockhounding.blocks;

import al132.atmrockhounding.enums.EnumAlloy;
import al132.atmrockhounding.recipes.ModArray;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
// PIN sends the signal vertically

public class ModBlocks {
	public static Block mineralOres;
	public static Block alloyBlocks;
	public static Block alloyBricks;

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
		alloyBricks = new AlloyBricks(Material.IRON, EnumAlloy.getLowerNameArray(), 3.0F, 5.0F, "alloyBricks", SoundType.METAL);

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
		registerMetaBlock(alloyBricks, "alloyBricks");
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


	//recall the renders
	public static void initClient(){
		//blocks
		for(int i = 0; i < ModArray.mineralOresArray.length; i++){		registerMetaBlockRender(mineralOres, i, ModArray.mineralOresArray[i]);			}
		//TODO for(int i = 0; i < ModArray.saltMakerArray.length; i++){		registerMetaBlockRender(saltMaker, i, ModArray.saltMakerArray[i]);				}
		for(int i = 0; i < EnumAlloy.size(); i++){			registerMetaBlockRender(alloyBlocks, i, EnumAlloy.getName(i));				}
		for(int i = 0; i < EnumAlloy.size(); i++){			registerMetaBlockRender(alloyBricks, i, EnumAlloy.getName(i));				}
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
