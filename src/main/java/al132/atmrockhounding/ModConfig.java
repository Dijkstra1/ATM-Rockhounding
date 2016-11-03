package al132.atmrockhounding;

import al132.atmrockhounding.blocks.SaltMaker;
import al132.atmrockhounding.tile.TileSaltMaker;
import al132.atmrockhounding.world.ChemOresGenerator;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModConfig {
	public static final String CATEGORY_MINERAL = "Mineral";
	public static final String CATEGORY_SALT = "Salt";
	public static final String CATEGORY_TOONS = "Tools";

	public static int speedLabOven;
	public static int speedSizer;
	public static int speedAnalyzer;
	public static int speedExtractor;
	public static int speedAlloyer;
	public static int factorExtractor; // ?
	
	//config
	public static int maxMineral;
	public static int gearUses;
	public static int tubeUses;
	public static int patternUses;
	public static boolean enableRainRefill;
	public static boolean forceSmelting;

	
	public static void loadConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

	//ORES
		config.addCustomCategoryComment("Mineral", "These settings handle the Mineral spawning parameters.");
		ChemOresGenerator.mineralFrequency = config.get(			CATEGORY_MINERAL, "MineralFrequency", 		12, 	"Frequency of the Uninspected Mineral spawning").getInt();
		ChemOresGenerator.mineralMaxVein = config.get(				CATEGORY_MINERAL, "MineralMaxVein", 		20, 	"Highest mineral vein size").getInt();
		ChemOresGenerator.mineralMinVein = config.get(				CATEGORY_MINERAL, "MineralMinVein", 		10,		"Lowest mineral vein size").getInt();
		ChemOresGenerator.mineralMaxLevel = config.get(				CATEGORY_MINERAL, "MineralMaxLevel", 		200, 	"Highest mineral level").getInt();
		ChemOresGenerator.mineralMinLevel = config.get(				CATEGORY_MINERAL, "MineralMinLevel", 		27,		"Lowest mineral level").getInt();
		ChemOresGenerator.aromaFrequency = config.get(				CATEGORY_MINERAL, "AromaMineralFrequency", 	16, 	"Frequency of the Uninspected Mineral in the Aroma Mining Dimension").getInt();
		ChemOresGenerator.aromaMaxVein = config.get(				CATEGORY_MINERAL, "AromaMineralMaxVein", 	24, 	"Highest mineral vein size in the Aroma Mining Dimension").getInt();
		ChemOresGenerator.aromaMinVein = config.get(				CATEGORY_MINERAL, "AromaMineralMinVein", 	10,		"Lowest mineral vein size in the Aroma Mining Dimension").getInt();
		ChemOresGenerator.aromaMaxLevel = config.get(				CATEGORY_MINERAL, "AromaMineralMaxLevel", 	75, 	"Highest mineral level in the Aroma Mining Dimension. Must fit the Dimension height").getInt();
		ChemOresGenerator.aromaMinLevel = config.get(				CATEGORY_MINERAL, "AromaMineralMinLevel", 	20,		"Lowest mineral level in the Aroma Mining Dimension").getInt();
		forceSmelting = config.get(									CATEGORY_MINERAL, "ForceSmelting", 			true,	"Force the smelting of dusts into other mod ingots when available").getBoolean();
		ChemOresGenerator.enableAromaDimension = config.get(		CATEGORY_MINERAL, "AromaDimensionEnabler", 	true,	"Spawn the mineral in the Aroma Mining Dimension").getBoolean();
		ChemOresGenerator.aromaDimension = config.get(				CATEGORY_MINERAL, "AromaDimensionID", 		6,		"ID of the Aroma DImension. Must match the one in the Aroma config").getInt();

	//SALT
		config.addCustomCategoryComment("Salt", "These settings handle the making of Salt.");
		TileSaltMaker.evaporationMultiplier = config.get(		CATEGORY_SALT, "EvaporationMultiplier", 	4, 		"Multiply ticks to advance in the evaporation process. Base ticks = 2000").getInt();
		enableRainRefill = config.get(								CATEGORY_SALT, "RefillTankFromRain", 		true,	"Wether the rain can automatically refill the tank when empty").getBoolean();
		SaltMaker.saltAmount = config.get(							CATEGORY_SALT, "SaltQuantity", 				4, 		"Max quantity of salt items a single tank can produce").getInt();

	//TOOLS
		config.addCustomCategoryComment("Tools", "These settings handle the settings of machines and tools.");
		speedLabOven = config.get(									CATEGORY_TOONS, "SpeedLabOven", 			200,	"Ticks required to produce acids in the Lab Oven").getInt();
		speedSizer = config.get(				CATEGORY_TOONS, "SpeedMineralSizer", 		300,	"Ticks required to crush minerals in the Mineral Sizer").getInt();
		speedAnalyzer = config.get(			CATEGORY_TOONS, "SpeedMineralAnalyzer", 	400,	"Ticks required to analyze minerals in the Mineral Analyzer").getInt();
		speedExtractor = config.get(			CATEGORY_TOONS, "SpeedChemicalExtractor", 	600,	"Ticks required to extract elements in the Chemical Extractor").getInt();
		speedAlloyer = config.get(				CATEGORY_TOONS, "SpeedMetalAlloyer", 		200,	"Ticks required to cast an alloy in the Metal Alloyer").getInt();
		factorExtractor = config.get(		CATEGORY_TOONS, "ExtractingFactor", 		100,	"Percentage of element required to produce one regular dust").getInt();
		gearUses = config.get(										CATEGORY_TOONS, "UsesGear", 				150,	"Max uses for the Crushing Gear in the Mineral Sizer").getInt();
		tubeUses = config.get(										CATEGORY_TOONS, "UsesTube", 				200,	"Max uses for the Test Tube in the Mineral Analyzer").getInt();
		patternUses = config.get(									CATEGORY_TOONS, "UsesPattern", 				100,	"Max uses for the Ingot Pattern in the Metal Alloyer").getInt();
		maxMineral = config.get(									CATEGORY_TOONS, "MaxMineralShards", 		4,		"Max amount of mineral shards obtainable from the Mineral Analyzer").getInt();
		
		config.save();
	}
}
