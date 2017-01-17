package al132.atmrockhounding.recipes;

import static al132.atmrockhounding.enums.EnumAlloy.BAM;
import static al132.atmrockhounding.enums.EnumAlloy.CORTEN;
import static al132.atmrockhounding.enums.EnumAlloy.CUBE;
import static al132.atmrockhounding.enums.EnumAlloy.CUPRONICKEL;
import static al132.atmrockhounding.enums.EnumAlloy.GREENGOLD;
import static al132.atmrockhounding.enums.EnumAlloy.HASTELLOY;
import static al132.atmrockhounding.enums.EnumAlloy.MISCHMETAL;
import static al132.atmrockhounding.enums.EnumAlloy.NICHROME;
import static al132.atmrockhounding.enums.EnumAlloy.NIMONIC;
import static al132.atmrockhounding.enums.EnumAlloy.PEWTER;
import static al132.atmrockhounding.enums.EnumAlloy.ROSEGOLD;
import static al132.atmrockhounding.enums.EnumAlloy.SCAL;
import static al132.atmrockhounding.enums.EnumAlloy.SHIBUICHI;
import static al132.atmrockhounding.enums.EnumAlloy.TOMBAK;
import static al132.atmrockhounding.enums.EnumAlloy.WHITEGOLD;
import static al132.atmrockhounding.enums.EnumAlloy.YAG;
import static al132.atmrockhounding.enums.EnumAlloy2.TUNGSTEEL;
import static al132.atmrockhounding.enums.EnumElement.ALUMINUM;
import static al132.atmrockhounding.enums.EnumElement.ARSENIC;
import static al132.atmrockhounding.enums.EnumElement.BERYLLIUM;
import static al132.atmrockhounding.enums.EnumElement.BISMUTH;
import static al132.atmrockhounding.enums.EnumElement.BORON;
import static al132.atmrockhounding.enums.EnumElement.CADMIUM;
import static al132.atmrockhounding.enums.EnumElement.CERIUM;
import static al132.atmrockhounding.enums.EnumElement.CHROMIUM;
import static al132.atmrockhounding.enums.EnumElement.COBALT;
import static al132.atmrockhounding.enums.EnumElement.COPPER;
import static al132.atmrockhounding.enums.EnumElement.GOLD;
import static al132.atmrockhounding.enums.EnumElement.IRON;
import static al132.atmrockhounding.enums.EnumElement.LANTHANUM;
import static al132.atmrockhounding.enums.EnumElement.LEAD;
import static al132.atmrockhounding.enums.EnumElement.MAGNESIUM;
import static al132.atmrockhounding.enums.EnumElement.MANGANESE;
import static al132.atmrockhounding.enums.EnumElement.NEODYMIUM;
import static al132.atmrockhounding.enums.EnumElement.NICKEL;
import static al132.atmrockhounding.enums.EnumElement.PHOSPHORUS;
import static al132.atmrockhounding.enums.EnumElement.PRASEODYMIUM;
import static al132.atmrockhounding.enums.EnumElement.SCANDIUM;
import static al132.atmrockhounding.enums.EnumElement.SILICON;
import static al132.atmrockhounding.enums.EnumElement.SILVER;
import static al132.atmrockhounding.enums.EnumElement.TIN;
import static al132.atmrockhounding.enums.EnumElement.TUNGSTEN;
import static al132.atmrockhounding.enums.EnumElement.YTTRIUM;
import static al132.atmrockhounding.enums.EnumElement.ZINC;
import static al132.atmrockhounding.enums.EnumElement.TITANIUM;

import java.util.ArrayList;

import al132.atmrockhounding.ModConfig;
import al132.atmrockhounding.blocks.ModBlocks;
import al132.atmrockhounding.enums.EnumAlloy;
import al132.atmrockhounding.enums.EnumAlloy2;
import al132.atmrockhounding.enums.EnumElement;
import al132.atmrockhounding.fluids.ModFluids;
import al132.atmrockhounding.items.ModItems;
import al132.atmrockhounding.recipes.machines.ChemicalExtractorRecipe;
import al132.atmrockhounding.recipes.machines.LabOvenRecipe;
import al132.atmrockhounding.recipes.machines.MetalAlloyerRecipe;
import al132.atmrockhounding.recipes.machines.MineralAnalyzerRecipe;
import al132.atmrockhounding.recipes.machines.MineralSizerRecipe;
import al132.atmrockhounding.utils.Utils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;


public class ModRecipes {
	public static final ArrayList<MineralSizerRecipe> sizerRecipes = new ArrayList<MineralSizerRecipe>();
	public static final ArrayList<LabOvenRecipe> labOvenRecipes = new ArrayList<LabOvenRecipe>();
	public static final ArrayList<MetalAlloyerRecipe> alloyerRecipes = new ArrayList<MetalAlloyerRecipe>();
	public static final ArrayList<MineralAnalyzerRecipe> analyzerRecipes = new ArrayList<MineralAnalyzerRecipe>();
	public static final ArrayList<ChemicalExtractorRecipe> extractorRecipes = new ArrayList<ChemicalExtractorRecipe>();

	public static ArrayList<MetalAlloyerRecipe> getImmutableAlloyerRecipes(){
		return new ArrayList<MetalAlloyerRecipe>(alloyerRecipes);
	}

	private static ItemStack ingotforcedstack;

	static ItemStack testTube = new ItemStack(ModItems.testTube);
	static ItemStack gear = new ItemStack(ModItems.gear);
	static ItemStack ingotPattern = new ItemStack(ModItems.ingotPattern);

	static ItemStack cabinet = new ItemStack(ModItems.itemCabinet);
	static ItemStack flask = new ItemStack(ModItems.flask);
	static ItemStack heatingElement = new ItemStack(ModItems.heatingElement);
	static ItemStack inductor = new ItemStack(ModItems.inductionHeatingElement);

	static ItemStack sulfuricBeaker = new ItemStack(ModItems.sulfuricBeaker);

	static ItemStack flasks = new ItemStack(ModItems.flask);
	static ItemStack crackedCoal = new ItemStack(ModItems.crackedCoalCompound);


	public static void init() {
		loadDictionary();
		craftingRecipes();
		machineRecipes();
	}


	public static void loadDictionary()  {
		OreDictionary.registerOre("dustSalt", new ItemStack(ModItems.salt, 1, 0));	
		OreDictionary.registerOre("itemFluorite", new ItemStack(ModItems.halideShards, 1, 4));	
		OreDictionary.registerOre("itemPyrite", new ItemStack(ModItems.sulfideShards, 1, 6));	

		for(int x = 0; x < EnumElement.size(); x++){
			OreDictionary.registerOre(EnumElement.getDustName(x), new ItemStack(ModItems.chemicalDusts, 1, x));	
		}

		for(int x = 0; x < EnumAlloy.size(); x++){
			OreDictionary.registerOre(EnumAlloy.getDictName("block", x), new ItemStack(ModBlocks.alloyBlocks, 1, x));
			OreDictionary.registerOre(EnumAlloy.getDictName("dust",x), new ItemStack(ModItems.alloyDusts, 1, x));	
			OreDictionary.registerOre(EnumAlloy.getDictName("ingot",x), new ItemStack(ModItems.alloyIngots, 1, x));	
			OreDictionary.registerOre(EnumAlloy.getDictName("nugget",x), new ItemStack(ModItems.alloyNuggets, 1, x));	
		}
		
		for(int x = 0; x < EnumAlloy2.size(); x++){
			OreDictionary.registerOre(EnumAlloy2.getDictName("block", x), new ItemStack(ModBlocks.alloyBlocks2, 1, x));
			OreDictionary.registerOre(EnumAlloy2.getDictName("dust",x), new ItemStack(ModItems.alloyDusts, 1, x+16));	
			OreDictionary.registerOre(EnumAlloy2.getDictName("ingot",x), new ItemStack(ModItems.alloyIngots, 1, x+16));	
			OreDictionary.registerOre(EnumAlloy2.getDictName("nugget",x), new ItemStack(ModItems.alloyNuggets, 1, x+16));	
		}
	}


	public static void machineRecipes(){
		sizerRecipes.add(new MineralSizerRecipe(ModBlocks.mineralOres,null));

		sizerRecipes.add(new MineralSizerRecipe(Blocks.STONE,1, ModItems.fluoriteCompound,0)); //fuorite dust

		labOvenRecipes.add(new LabOvenRecipe(new ItemStack(ModItems.sulfurCompound), new FluidStack(FluidRegistry.WATER,500), new FluidStack(ModFluids.SULFURIC_ACID,500)));
		labOvenRecipes.add(new LabOvenRecipe(new ItemStack(ModItems.sodiumCompound), new FluidStack(ModFluids.SULFURIC_ACID,500), new FluidStack(ModFluids.HYDROCHLORIC_ACID,500)));
		labOvenRecipes.add(new LabOvenRecipe(new ItemStack(ModItems.fluoriteCompound), new FluidStack(ModFluids.SULFURIC_ACID,500), new FluidStack(ModFluids.HYDROFLUORIC_ACID,500)));
		labOvenRecipes.add(new LabOvenRecipe(new ItemStack(ModItems.carbonCompound), new FluidStack(FluidRegistry.WATER,500), new FluidStack(ModFluids.SYNGAS,500)));


		//TODO find a more readable way to do this
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,1,ModItems.arsenateShards, new int[]{80,20}));
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,2,ModItems.borateShards, new int[]{25,15,15,20,10,15}));
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,3,ModItems.carbonateShards, new int[]{55,20,20,5}));
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,4,ModItems.halideShards, new int[]{25,15,15,15,30}));
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,5,ModItems.nativeShards, new int[]{6,6,10,8,8,10,8,8,9,8,9,8}));
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,6,ModItems.oxideShards, new int[]{9,9,4,4,11,4,4,4,4,4,4,4,4,4,4,4,5,5,5,5}));
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,7,ModItems.phosphateShards, new int[]{8,8,8,8,8,8,8,8,8,8,8,8}));
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,8,ModItems.silicateShards, new int[]{ 8,8,8,8,8,8,8,8,8,8,8,8}));
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,9,ModItems.sulfateShards, new int[]{11,11,11,11,11,11,11,11,11}));
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,10,ModItems.sulfideShards, new int[]{ 6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6}));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,9, CUBE.ordinal()),
				Utils.getOres(COPPER.getDustName(),7),
				Utils.getOres(BERYLLIUM.getDustName(),2)));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,9,SCAL.ordinal()),
				Utils.getOres(ALUMINUM.getDustName(),7),
				Utils.getOres(SCANDIUM.getDustName(),2)));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,9,BAM.ordinal()),
				Utils.getOres(BORON.getDustName(),6),
				Utils.getOres(ALUMINUM.getDustName(),2),
				Utils.getOres(MAGNESIUM.getDustName(),1)));


		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,9,YAG.ordinal()),
				Utils.getOres(YTTRIUM.getDustName(),4),
				Utils.getOres(ALUMINUM.getDustName(),2),
				Utils.getOres(NEODYMIUM.getDustName(),2),
				Utils.getOres(CHROMIUM.getDustName(),1)));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,9,CUPRONICKEL.ordinal()),
				Utils.getOres(COPPER.getDustName(),4),
				Utils.getOres(NICKEL.getDustName(),2),
				Utils.getOres(MANGANESE.getDustName(),2),
				Utils.getOres(IRON.getDustName(),1)));


		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,9,NIMONIC.ordinal()),
				Utils.getOres(NICKEL.getDustName(),5),
				Utils.getOres(COBALT.getDustName(),2),
				Utils.getOres(CHROMIUM.getDustName(),1)));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,9, HASTELLOY.ordinal()),
				Utils.getOres(IRON.getDustName(),4),
				Utils.getOres(NICKEL.getDustName(),3),
				Utils.getOres(CHROMIUM.getDustName(),1),
				Utils.getOres(MANGANESE.getDustName(),1)));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,9,NICHROME.ordinal()),
				Utils.getOres(NICKEL.getDustName(),6),
				Utils.getOres(CHROMIUM.getDustName(),2),
				Utils.getOres(IRON.getDustName(),1)));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,9,MISCHMETAL.ordinal()),
				Utils.getOres(CERIUM.getDustName(),4),
				Utils.getOres(LANTHANUM.getDustName(),2),
				Utils.getOres(NEODYMIUM.getDustName(),1),
				Utils.getOres(PRASEODYMIUM.getDustName(),1),
				Utils.getOres(IRON.getDustName(),1)));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,9,ROSEGOLD.ordinal()),
				Utils.getOres(GOLD.getDustName(),5),
				Utils.getOres(COPPER.getDustName(),3),
				Utils.getOres(SILVER.getDustName(),1)));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,9,GREENGOLD.ordinal()),
				Utils.getOres(GOLD.getDustName(),5),
				Utils.getOres(SILVER.getDustName(),2),
				Utils.getOres(COPPER.getDustName(),1),
				Utils.getOres(CADMIUM.getDustName(),1)));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,9,WHITEGOLD.ordinal()),
				Utils.getOres(GOLD.getDustName(),5),
				Utils.getOres(SILVER.getDustName(),2),
				Utils.getOres(COPPER.getDustName(),1),
				Utils.getOres(MANGANESE.getDustName(),1)));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,9,SHIBUICHI.ordinal()),
				Utils.getOres(COPPER.getDustName(),5),
				Utils.getOres(SILVER.getDustName(),2),
				Utils.getOres(GOLD.getDustName(),1),
				Utils.getOres(TITANIUM.getDustName(),1)));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,9,TOMBAK.ordinal()),
				Utils.getOres(COPPER.getDustName(), 6),
				Utils.getOres(ZINC.getDustName(), 2),
				Utils.getOres(ARSENIC.getDustName(), 1)));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,9,PEWTER.ordinal()),
				Utils.getOres(TIN.getDustName(),5),
				Utils.getOres(COPPER.getDustName(),1),
				Utils.getOres(BISMUTH.getDustName(),1),
				Utils.getOres(LEAD.getDustName(),1)));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,9,CORTEN.ordinal()),
				Utils.getOres(NICKEL.getDustName(), 2),
				Utils.getOres(SILICON.getDustName(), 2),
				Utils.getOres(CHROMIUM.getDustName(), 2),
				Utils.getOres(PHOSPHORUS.getDustName(), 1),
				Utils.getOres(MANGANESE.getDustName(), 1),
				Utils.getOres(COPPER.getDustName(), 1)));

		//EnumAlloy2
		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,9,TUNGSTEEL.ordinal()+16),
				Utils.getOres(IRON.getDustName(), 5),
				Utils.getOres(TUNGSTEN.getDustName(), 2),
				Utils.getOres(CHROMIUM.getDustName(), 1),
				Utils.getOres(COBALT.getDustName(),1)));

		
		/*extractorRecipes.add(new ChemicalExtractorRecipe(ModItems.arsenateShards,0,
				elementStack(COPPER,36),
				elementStack(ARSENIC,22),
				elementStack(LEAD,6)));*/
	}

	public static ElementStack elementStack(EnumElement element, int quantity){
		return new ElementStack(element,quantity);
	}

	public static FluidStack getLabOvenSolvent(ItemStack input){
		return null;
	}

	private static void craftingRecipes() {
		//alloy parts
		for(int x = 0; x < EnumAlloy.size(); x++){
			for(ItemStack ore : OreDictionary.getOres(EnumAlloy.getDictName("dust", x))) {
				if(ore != null)  {
					if(ore.getItemDamage() != -1 || ore.getItemDamage() != OreDictionary.WILDCARD_VALUE) {
						GameRegistry.addSmelting(ore, new ItemStack(ModItems.alloyIngots, 1, x), 1.0F);
					}
				}
			}
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.alloyIngots, 1, x ), new Object[] { "XXX", "XXX", "XXX", 'X', EnumAlloy.getDictName("nugget",x)}));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.alloyIngots, 9, x), new Object[] { EnumAlloy.getDictName("block", x)}));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.alloyNuggets, 9, x), new Object[] { new ItemStack(ModItems.alloyIngots,1,x) }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alloyBlocks, 1, x), new Object[] { "XXX", "XXX", "XXX", 'X', new ItemStack(ModItems.alloyIngots,1,x) }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alloyBricks, 4, x), new Object[] { "XX", "XX", 'X', EnumAlloy.getDictName("block", x)}));
		}
		
		for(int x = 0; x < EnumAlloy2.size(); x++){
			for(ItemStack ore : OreDictionary.getOres(EnumAlloy2.getDictName("dust", x))) {
				if(ore != null)  {
					if(ore.getItemDamage() != -1 || ore.getItemDamage() != OreDictionary.WILDCARD_VALUE) {
						GameRegistry.addSmelting(ore, new ItemStack(ModItems.alloyIngots, 1, x+16), 1.0F);
					}
				}
			}
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.alloyIngots, 1, x+16 ), new Object[] { "XXX", "XXX", "XXX", 'X', EnumAlloy2.getDictName("nugget",x)}));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.alloyIngots, 9, x+16), new Object[] { EnumAlloy2.getDictName("block", x)}));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.alloyNuggets, 9, x+16), new Object[] { new ItemStack(ModItems.alloyIngots,1,x+16) }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alloyBlocks2, 1, x), new Object[] { "XXX", "XXX", "XXX", 'X', new ItemStack(ModItems.alloyIngots,1,x+16) }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alloyBricks2, 4, x), new Object[] { "XX", "XX", 'X', EnumAlloy2.getDictName("block", x)}));
		}

		//lab oven
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.labOven), new Object[] { "IBI", "IGI", "IFI", 'B', flask, 'I', "ingotIron", 'F', Blocks.FURNACE, 'G', "blockGlass" }));
		//mineral sizer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.mineralSizer), new Object[] { "IHI", "GcG", "ICI", 'C', cabinet, 'I', "ingotIron", 'G', gear, 'c', Items.COMPARATOR, 'H', Blocks.HOPPER }));
		//mineral analyzer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.mineralAnalyzer), new Object[] { "BBB", "IcI", "III", 'B', Items.BUCKET, 'I', "ingotIron", 'c', Items.COMPARATOR }));
		//chemical extractor
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.chemicalExtractor), new Object[] { "CCC", "IcI", "III", 'C', cabinet, 'I', "ingotIron", 'c', Items.COMPARATOR }));
		//salt maker
		//TODO GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.saltMaker), new Object[] { "S S", "sss", 's', "stone", 'S', "ingotBrick" }));
		//metal alloyer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.metalAlloyer), new Object[] { "bFb", "III", "ITI", 'I', "ingotIron", 'F', Blocks.FURNACE, 'b', Items.BOWL, 'T', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE }));

		//gear
		GameRegistry.addRecipe(new ShapedOreRecipe(gear, new Object[] { " I ","III"," I ", 'I', "ingotIron"}));
		//cabinet
		GameRegistry.addRecipe(new ShapedOreRecipe(cabinet, new Object[] { "GGG","GIG","GGG", 'I', "ingotIron", 'G', "blockGlass" }));
		//iron ingot
		GameRegistry.addRecipe(new ShapedOreRecipe(Items.IRON_INGOT, new Object[] { "NNN","NNN","NNN", 'N', "nuggetIron" }));
		//test tube
		GameRegistry.addRecipe(new ShapedOreRecipe(testTube, new Object[] { "  G"," G ","G  ", 'G', "blockGlass" }));
		//flask
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.flask,4), new Object[] { " G ","G G","GGG", 'G', "blockGlass"}));
		//ingot pattern
		GameRegistry.addRecipe(new ShapedOreRecipe(ingotPattern, new Object[] { "T","P", 'T', Blocks.IRON_TRAPDOOR, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE}));
		//heater
		GameRegistry.addRecipe(new ShapedOreRecipe(heatingElement, new Object[] { "NNN", "N N", "I I", 'I', "ingotIron", 'N', "ingotNichrome"}));
		//inductor
		GameRegistry.addRecipe(new ShapedOreRecipe(inductor, new Object[] { "III", "IHI", "III", 'I', "ingotIron", 'H', heatingElement}));

		//sulfur compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.sulfurCompound, 8), new Object[] { flask, "dustSulfur", "dustSulfur", "dustSulfur" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.sulfurCompound, 8), new Object[] { flask, "gunpowder" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.sulfurCompound, 8), new Object[] { flask, "itemPyrite", "itemPyrite", "itemPyrite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.sulfurCompound, 8), new Object[] { flask, "itemAnthracite", "itemAnthracite", "itemAnthracite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.sulfurCompound, 4), new Object[] { flask, "itemBituminous", "itemBituminous", "itemBituminous" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.sulfurCompound, 2), new Object[] { flask, "itemLignite", "itemLignite", "itemLignite" }));
		//sodium chloride compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.sodiumCompound, 8), new Object[] { flask, "dustSalt", "dustSalt", "dustSalt" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.sodiumCompound, 8), new Object[] { flask, "itemSalt", "itemSalt", "itemSalt" }));
		//fluorite compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.fluoriteCompound, 8), new Object[] { flask, "dustFluorite", "dustFluorite", "dustFluorite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.fluoriteCompound, 8), new Object[] { flask, "itemFluorite", "itemFluorite", "itemFluorite" }));
		//cracked coal
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.crackedCoalCompound, 1), new Object[] { flask, Items.COAL, Items.COAL, Items.COAL }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.crackedCoalCompound, 4), new Object[] { flask, "blockAnthracite"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.crackedCoalCompound, 3), new Object[] { flask, "blockBituminous"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.crackedCoalCompound, 2), new Object[] { flask, "blockcCoal"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.crackedCoalCompound, 1), new Object[] { flask, "blockLignite"}));
		//carbon compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.carbonCompound, 1), new Object[] { crackedCoal, crackedCoal, crackedCoal, crackedCoal}));
		//Energized fuel blend
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.energized_fuel_blend,9), new Object[] { "RRR", "CGC", "RRR", 'R', "dustRedstone", 'G', "dustGlowstone", 'C', "coal"}));
		
		//force smelting
		if(ModConfig.forceSmelting){
			for(int x = 0; x < EnumElement.size(); x++){
				ingotforcedstack = null;
				for(ItemStack ingot : OreDictionary.getOres(EnumElement.getIngotName(x))) {
					if(ingot != null){ingotforcedstack = ingot;}

				}
				if(ingotforcedstack != null){ 
					GameRegistry.addSmelting(new ItemStack(ModItems.chemicalDusts, 1, x), ingotforcedstack, 1.0F);
				}
			}
		}else{
			//gold ingot
			GameRegistry.addSmelting(new ItemStack(ModItems.chemicalDusts, 1, 45), new ItemStack(Items.GOLD_INGOT), 1.0F);
			//iron ingot
			GameRegistry.addSmelting(new ItemStack(ModItems.chemicalDusts, 1, 16), new ItemStack(Items.IRON_INGOT), 1.0F);
		}
	}
}