package al132.atmrockhounding.recipes;

import java.util.ArrayList;

import al132.atmrockhounding.ModConfig;
import al132.atmrockhounding.blocks.ModBlocks;
import al132.atmrockhounding.enums.EnumAlloy;
import al132.atmrockhounding.enums.EnumElement;
import al132.atmrockhounding.enums.EnumFluid;
import al132.atmrockhounding.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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

	private static ItemStack dustforcedstack;
	private static ItemStack ingotforcedstack;

	static ItemStack testTube = new ItemStack(ModItems.testTube);
	static ItemStack gear = new ItemStack(ModItems.gear);
	static ItemStack ingotPattern = new ItemStack(ModItems.ingotPattern);
	static ItemStack cylinder = new ItemStack(ModItems.cylinder);

	static ItemStack cabinet = new ItemStack(ModItems.itemCabinet);
	static ItemStack flask = new ItemStack(ModItems.flask);
	static ItemStack heatingElement = new ItemStack(ModItems.heatingElement);
	static ItemStack inductor = new ItemStack(ModItems.inductionHeatingElement);

	static ItemStack sulfuricBeaker = new ItemStack(ModItems.sulfuricBeaker);

	static ItemStack flasks = new ItemStack(ModItems.flask);
	static ItemStack crackedCoal = new ItemStack(ModItems.chemicalItems, 1, 6);
	static ItemStack chemicalTank = new ItemStack(ModItems.chemicalItems, 1, 0);


	public static void init() {
		craftingRecipes();
		machineRecipes();
	}



	public static void machineRecipes(){
		sizerRecipes.add(new MineralSizerRecipe(ModBlocks.mineralOres,null));
		//sizerRecipes.add(new MineralSizerRecipe(Items.IRON_INGOT, 0, ModItems.chemicalDusts,16)); //iron dust
		//sizerRecipes.add(new MineralSizerRecipe(Items.GOLD_INGOT,0, ModItems.chemicalDusts,45)); //gold dust
		sizerRecipes.add(new MineralSizerRecipe(Blocks.STONE,1, ModItems.chemicalItems,4)); //fuorite dust

		labOvenRecipes.add(new LabOvenRecipe(ModItems.chemicalItems,2, EnumFluid.WATER, EnumFluid.SULFURIC_ACID));
		labOvenRecipes.add(new LabOvenRecipe(ModItems.chemicalItems,3, EnumFluid.SULFURIC_ACID, EnumFluid.HYDROCHLORIC_ACID));
		labOvenRecipes.add(new LabOvenRecipe(ModItems.chemicalItems,4, EnumFluid.SULFURIC_ACID, EnumFluid.HYDROFLUORIC_ACID));
		labOvenRecipes.add(new LabOvenRecipe(ModItems.chemicalItems,5, EnumFluid.WATER, EnumFluid.SYNGAS));


		//TODO find a more readable way to do this
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,1,ModItems.arsenateShards, new int[]{80,20}));
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,2,ModItems.borateShards, new int[]{25,15,15,20,10,15}));
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,3,ModItems.carbonateShards, new int[]{55,20,20,5}));
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,4,ModItems.halideShards, new int[]{25,15,15,15,30}));
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,5,ModItems.nativeShards, new int[]{6,6,10,8,8,10,8,8,9,8,9,8}));
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,6,ModItems.oxideShards, new int[]{6,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5}));
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,7,ModItems.phosphateShards, new int[]{8,8,8,8,8,8,8,8,8,8,8,8}));
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,8,ModItems.silicateShards, new int[]{ 8,8,8,8,8,8,8,8,8,8,8,8}));
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,9,ModItems.sulfateShards, new int[]{11,11,11,11,11,11,11,11,11}));
		analyzerRecipes.add(new MineralAnalyzerRecipe(ModBlocks.mineralOres,10,ModItems.sulfideShards, new int[]{ 6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6}));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,8,EnumAlloy.CUBE.ordinal()),
				7, EnumElement.COPPER.ordinal(),
				2, EnumElement.BERYLLIUM.ordinal()));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,8,EnumAlloy.SCAL.ordinal()),
				7, EnumElement.ALUMINUM.ordinal(),
				2, EnumElement.SCANDIUM.ordinal()));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,8,EnumAlloy.BAM.ordinal()),
				6, EnumElement.BORON.ordinal(),
				2, EnumElement.ALUMINUM.ordinal(),
				1, EnumElement.MAGNESIUM.ordinal()));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,8,EnumAlloy.YAG.ordinal()),
				4, EnumElement.YTTRIUM.ordinal(),
				2, EnumElement.ALUMINUM.ordinal(),
				2, EnumElement.NEODYMIUM.ordinal(),
				1, EnumElement.CHROMIUM.ordinal()));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,8,EnumAlloy.CUPRONICKEL.ordinal()),
				4, EnumElement.COPPER.ordinal(),
				2, EnumElement.NICKEL.ordinal(),
				2, EnumElement.MANGANESE.ordinal(),
				1, EnumElement.IRON.ordinal()));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,8,EnumAlloy.NIMONIC.ordinal()),
				5, EnumElement.NICKEL.ordinal(),
				2, EnumElement.COBALT.ordinal(),
				2, EnumElement.CHROMIUM.ordinal()));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,8,EnumAlloy.HASTELLOY.ordinal()),
				5, EnumElement.IRON.ordinal(),
				3, EnumElement.NICKEL.ordinal(),
				1, EnumElement.CHROMIUM.ordinal()));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,8,EnumAlloy.NICHROME.ordinal()),
				6, EnumElement.NICKEL.ordinal(),
				2, EnumElement.CHROMIUM.ordinal(),
				1, EnumElement.IRON.ordinal()));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,8,EnumAlloy.MISCHMETAL.ordinal()),
				4, EnumElement.CERIUM.ordinal(),
				2, EnumElement.LANTHANUM.ordinal(),
				1, EnumElement.NEODYMIUM.ordinal(),
				1, EnumElement.PRASEODYMIUM.ordinal(),
				1, EnumElement.IRON.ordinal()));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,8,EnumAlloy.ROSEGOLD.ordinal()),
				5, EnumElement.GOLD.ordinal(),
				3, EnumElement.COPPER.ordinal(),
				1, EnumElement.SILVER.ordinal()));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,8,EnumAlloy.GREENGOLD.ordinal()),
				5, EnumElement.GOLD.ordinal(),
				2, EnumElement.SILVER.ordinal(),
				1, EnumElement.COPPER.ordinal(),
				1, EnumElement.CADMIUM.ordinal()));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,8,EnumAlloy.WHITEGOLD.ordinal()),
				5, EnumElement.GOLD.ordinal(),
				2, EnumElement.SILVER.ordinal(),
				1, EnumElement.COPPER.ordinal(),
				1, EnumElement.MANGANESE.ordinal()));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,8,EnumAlloy.SHIBUICHI.ordinal()),
				7, EnumElement.COPPER.ordinal(),
				2, EnumElement.SILVER.ordinal(),
				1, EnumElement.GOLD.ordinal()));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,8,EnumAlloy.TOMBAK.ordinal()),
				6, EnumElement.COPPER.ordinal(),
				2, EnumElement.ZINC.ordinal(),
				1, EnumElement.ARSENIC.ordinal()));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,8,EnumAlloy.PEWTER.ordinal()),
				5, EnumElement.TIN.ordinal(),
				1, EnumElement.COPPER.ordinal(),
				1, EnumElement.BISMUTH.ordinal(),
				1, EnumElement.LEAD.ordinal()));

		alloyerRecipes.add(new MetalAlloyerRecipe(
				new ItemStack(ModItems.alloyIngots,8,EnumAlloy.CORTEN.ordinal()),
				2, EnumElement.NICKEL.ordinal(),
				2, EnumElement.SILICON.ordinal(),
				2, EnumElement.CHROMIUM.ordinal(),
				1, EnumElement.PHOSPHORUS.ordinal(),
				1, EnumElement.MANGANESE.ordinal(),
				1, EnumElement.COPPER.ordinal()));
	}


	public static EnumFluid getLabOvenSolvent(ItemStack input){
		for(LabOvenRecipe recipe: labOvenRecipes){
			if(ItemStack.areItemsEqual(input, recipe.getSolute())){
				return recipe.getSolvent();
			}
		}
		return EnumFluid.EMPTY;
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
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.alloyIngots, 9, x), new Object[] { EnumAlloy.getDictName("blocks", x)}));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.alloyNuggets, 9, x), new Object[] { new ItemStack(ModItems.alloyIngots,1,x).getItem() }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alloyBlocks, 1, x), new Object[] { "XXX", "XXX", "XXX", 'X', new ItemStack(ModItems.alloyIngots,1,x).getItem() }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alloyBricks, 4, x), new Object[] { "XX", "XX", 'X', EnumAlloy.getDictName("blocks", x)}));
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

		//chemical tank
		GameRegistry.addRecipe(new ShapedOreRecipe(chemicalTank, new Object[] { "GCG", "G G", "GGG", 'G', "blockGlass", 'C', flask }));
		//cylinder
		GameRegistry.addRecipe(new ShapedOreRecipe(cylinder, new Object[] { " G "," G ","GGG", 'G', "blockGlass" }));
		//gear
		GameRegistry.addRecipe(new ShapedOreRecipe(gear, new Object[] { " I ","III"," I ", 'I', "ingotIron"}));
		//cabinet
		GameRegistry.addRecipe(new ShapedOreRecipe(cabinet, new Object[] { "GGG","GIG","GGG", 'I', "ingotIron", 'G', "blockGlass" }));
		//iron ingot
		GameRegistry.addRecipe(new ShapedOreRecipe(Items.IRON_INGOT, new Object[] { "NNN","NNN","NNN", 'N', "nuggetIron" }));
		//test tube
		GameRegistry.addRecipe(new ShapedOreRecipe(testTube, new Object[] { "  G"," G ","G  ", 'G', "blockGlass" }));
		//flask
		GameRegistry.addRecipe(new ShapedOreRecipe(flasks, new Object[] { " G ","G G","GGG", 'G', "blockGlass"}));
		//ingot pattern
		GameRegistry.addRecipe(new ShapedOreRecipe(ingotPattern, new Object[] { "T","P", 'T', Blocks.IRON_TRAPDOOR, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE}));
		//heater
		GameRegistry.addRecipe(new ShapedOreRecipe(heatingElement, new Object[] { "NNN", "N N", "I I", 'I', "ingotIron", 'N', "ingotNichrome"}));
		//inductor
		GameRegistry.addRecipe(new ShapedOreRecipe(inductor, new Object[] { "III", "HHH", "III", 'I', "ingotIron", 'H', heatingElement}));

		//sulfur compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 8, 2), new Object[] { flask, "dustSulfur", "dustSulfur", "dustSulfur" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 8, 2), new Object[] { flask, "gunpowder" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 8, 2), new Object[] { flask, "itemPyrite", "itemPyrite", "itemPyrite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 8, 2), new Object[] { flask, "itemAnthracite", "itemAnthracite", "itemAnthracite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 2), new Object[] { flask, "itemBituminous", "itemBituminous", "itemBituminous" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 2, 2), new Object[] { flask, "itemLignite", "itemLignite", "itemLignite" }));
		//sodium chloride compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 8, 3), new Object[] { flask, "dustSalt", "dustSalt", "dustSalt" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 8, 3), new Object[] { flask, "itemSalt", "itemSalt", "itemSalt" }));
		//fluorite compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 8, 4), new Object[] { flask, "dustFluorite", "dustFluorite", "dustFluorite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 8, 4), new Object[] { flask, "itemFluorite", "itemFluorite", "itemFluorite" }));
		//cracked coal
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 6), new Object[] { flask, Items.COAL, Items.COAL, Items.COAL }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 6), new Object[] { flask, "blockAnthracite"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 3, 6), new Object[] { flask, "blockBituminous"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 2, 6), new Object[] { flask, "blockcCoal"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 6), new Object[] { flask, "blockLignite"}));
		//carbon compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 5), new Object[] { crackedCoal, crackedCoal, crackedCoal, crackedCoal}));
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