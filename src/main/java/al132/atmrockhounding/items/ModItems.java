package al132.atmrockhounding.items;

import java.util.EnumSet;

import al132.atmrockhounding.ModConfig;
import al132.atmrockhounding.enums.EnumAlloy;
import al132.atmrockhounding.enums.EnumElement;
import al132.atmrockhounding.enums.shards.EnumArsenate;
import al132.atmrockhounding.enums.shards.EnumBorate;
import al132.atmrockhounding.enums.shards.EnumCarbonate;
import al132.atmrockhounding.enums.shards.EnumHalide;
import al132.atmrockhounding.enums.shards.EnumNative;
import al132.atmrockhounding.enums.shards.EnumOxide;
import al132.atmrockhounding.enums.shards.EnumPhosphate;
import al132.atmrockhounding.enums.shards.EnumSilicate;
import al132.atmrockhounding.enums.shards.EnumSulfate;
import al132.atmrockhounding.enums.shards.EnumSulfide;
import al132.atmrockhounding.utils.EnumUtils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ModItems {

	public static MineralShards arsenateShards;
	public static MineralShards borateShards;
	public static MineralShards carbonateShards;
	public static MineralShards halideShards;
	public static MineralShards nativeShards;
	public static MineralShards oxideShards;
	public static MineralShards phosphateShards;
	public static MineralShards silicateShards;
	public static MineralShards sulfateShards;
	public static MineralShards sulfideShards;
	public static ItemMetaBase chemicalDusts;

	public static ItemMetaBase alloyIngots;
	public static ItemMetaBase alloyNuggets;
	public static ItemMetaBase alloyDusts;

	public static ItemConsumable gear;
	public static ItemConsumable testTube;
	public static ItemConsumable cylinder;
	public static ItemConsumable ingotPattern;

	public static ItemBase itemCabinet;
	public static ItemBase heatingElement;
	public static ItemBase inductionHeatingElement;
	public static ItemBase flask;

	public static ItemBase sulfuricBeaker;
	public static ItemBase salt;
	public static ItemBase sulfurCompound;
	public static ItemBase sodiumCompound;
	public static ItemBase fluoriteCompound;
	public static ItemBase carbonCompound;
	public static ItemBase crackedCoalCompound;


	public static void init(){
		//items
		arsenateShards = new MineralShards("arsenateShards", EnumUtils.getNameArray(EnumArsenate.class));
		borateShards = new MineralShards("borateShards",EnumUtils.getNameArray(EnumBorate.class));
		carbonateShards = new MineralShards("carbonateShards", EnumUtils.getNameArray(EnumCarbonate.class));
		halideShards = new MineralShards("halideShards", EnumUtils.getNameArray(EnumHalide.class));
		nativeShards = new MineralShards("nativeShards",EnumUtils.getNameArray(EnumNative.class));
		oxideShards = new MineralShards("oxideShards", EnumUtils.getNameArray(EnumOxide.class));
		phosphateShards = new MineralShards("phosphateShards", EnumUtils.getNameArray(EnumPhosphate.class));
		silicateShards = new MineralShards("silicateShards", EnumUtils.getNameArray(EnumSilicate.class));
		sulfateShards = new MineralShards("sulfateShards", EnumUtils.getNameArray(EnumSulfate.class));
		sulfideShards = new MineralShards("sulfideShards", EnumUtils.getNameArray(EnumSulfide.class));
		chemicalDusts = new ItemMetaBase("chemicalDusts", EnumElement.getNames());

		gear = new ItemConsumable("gear", ModConfig.gearUses);
		testTube = new ItemConsumable("testTube", ModConfig.tubeUses);
		cylinder = new ItemConsumable("cylinder", ModConfig.tubeUses);
		ingotPattern = new ItemConsumable("ingotPattern", ModConfig.patternUses);
		itemCabinet = new ItemBase("itemCabinet");
		heatingElement = new ItemBase("heatingElement");
		inductionHeatingElement = new ItemBase("inductionHeatingElement");
		flask = new ItemBase("flask");
		
		salt = new ItemBase("salt");
		sulfurCompound = new ItemBase("sulfurCompound");
		sodiumCompound = new ItemBase("sodiumCompound");
		fluoriteCompound = new ItemBase("fluoriteCompound");
		carbonCompound = new ItemBase("carbonCompound");
		crackedCoalCompound = new ItemBase("crackedCoalCompound");


		alloyDusts = new ItemMetaBase("dust", EnumAlloy.getOreArray("dust"));
		alloyIngots = new ItemMetaBase("ingot", EnumAlloy.getOreArray("ingot"));
		alloyNuggets = new ItemMetaBase("nugget", EnumAlloy.getOreArray("nugget"));
		
	}
	

	
	public static void initClient(){

		reg(EnumArsenate.class,arsenateShards);
		reg(EnumBorate.class,borateShards);
		reg(EnumCarbonate.class,carbonateShards);
		reg(EnumHalide.class,halideShards);
		reg(EnumNative.class,nativeShards);
		reg(EnumOxide.class,oxideShards);
		reg(EnumPhosphate.class,phosphateShards);
		reg(EnumSilicate.class,silicateShards);
		reg(EnumSulfate.class,sulfateShards);
		reg(EnumSulfide.class,sulfideShards);
		
		for(int i = 0; i < EnumElement.size(); i++){
			registerMetaItemRender(chemicalDusts, i, EnumElement.getName(i));		
		}

		
		EnumSet.allOf(EnumAlloy.class).stream().forEach(x -> {
			ModItems.registerMetaExperimental(alloyDusts,x.ordinal(),EnumAlloy.getDictName("", x.ordinal()));
			ModItems.registerMetaExperimental(alloyIngots,x.ordinal(),EnumAlloy.getDictName("", x.ordinal()));
			ModItems.registerMetaExperimental(alloyNuggets,x.ordinal(),EnumAlloy.getDictName("", x.ordinal()));
		});

		gear.initModel();
		testTube.initModel();
		cylinder.initModel();
		ingotPattern.initModel();
		itemCabinet.initModel();
		heatingElement.initModel();
		inductionHeatingElement.initModel();
		flask.initModel();
		salt.initModel();
		sulfurCompound.initModel();
		sodiumCompound.initModel();
		fluoriteCompound.initModel();
		carbonCompound.initModel();
		crackedCoalCompound.initModel();

	}

	//wewlad. Overkill for sure. I'm just doing this to learn lambdas/generics
	public static <T extends Enum<T>> void reg(Class<T> enumClass, Item item){
		EnumSet.allOf(enumClass).stream().forEach(i -> {
			registerMetaItemRender(item, i.ordinal(), EnumUtils.getName(enumClass, i.ordinal()));
		});
	}

	//render meta item
	public static void registerMetaItemRender(Item item, int meta, String fileName){
		ModelResourceLocation model = new ModelResourceLocation(item.getRegistryName() + "_" + fileName, "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, model );
	}
	

	public static void registerMetaExperimental(Item item, int meta, String fileName){
		ModelResourceLocation model = new ModelResourceLocation(item.getRegistryName() + fileName, "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, model );
	}
}
