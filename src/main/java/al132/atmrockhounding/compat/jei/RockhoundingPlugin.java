package al132.atmrockhounding.compat.jei;

import al132.atmrockhounding.blocks.ModBlocks;
import al132.atmrockhounding.client.gui.GuiLabOven;
import al132.atmrockhounding.client.gui.GuiMetalAlloyer;
import al132.atmrockhounding.client.gui.GuiMineralAnalyzer;
import al132.atmrockhounding.client.gui.GuiMineralSizer;
import al132.atmrockhounding.compat.jei.alloyer.AlloyerRecipeCategory;
import al132.atmrockhounding.compat.jei.alloyer.AlloyerRecipeHandler;
import al132.atmrockhounding.compat.jei.alloyer.AlloyerRecipeMaker;
import al132.atmrockhounding.compat.jei.analyzer.AnalyzerRecipeCategory;
import al132.atmrockhounding.compat.jei.analyzer.AnalyzerRecipeHandler;
import al132.atmrockhounding.compat.jei.analyzer.AnalyzerRecipeMaker;
import al132.atmrockhounding.compat.jei.extractor.ExtractorRecipeCategory;
import al132.atmrockhounding.compat.jei.extractor.ExtractorRecipeHandler;
import al132.atmrockhounding.compat.jei.extractor.ExtractorRecipeMaker;
import al132.atmrockhounding.compat.jei.laboven.LabOvenRecipeCategory;
import al132.atmrockhounding.compat.jei.laboven.LabOvenRecipeHandler;
import al132.atmrockhounding.compat.jei.laboven.LabOvenRecipeMaker;
import al132.atmrockhounding.compat.jei.sizer.SizerRecipeCategory;
import al132.atmrockhounding.compat.jei.sizer.SizerRecipeHandler;
import al132.atmrockhounding.compat.jei.sizer.SizerRecipeMaker;
import al132.atmrockhounding.enums.EnumElement;
import al132.atmrockhounding.items.ModItems;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class RockhoundingPlugin extends BlankModPlugin{

	public static IJeiHelpers jeiHelpers;

	@Override
	public void register(IModRegistry registry) {

		jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

		registry.addRecipeCategories(
				new SizerRecipeCategory(guiHelper),
				new AnalyzerRecipeCategory(guiHelper),
				new ExtractorRecipeCategory(guiHelper),
				new AlloyerRecipeCategory(guiHelper),
				new LabOvenRecipeCategory(guiHelper)
				);

		registry.addRecipeHandlers(
				new SizerRecipeHandler(),
				new AnalyzerRecipeHandler(),
				new ExtractorRecipeHandler(),
				new AlloyerRecipeHandler(),
				new LabOvenRecipeHandler()
				);

		registry.addRecipes(SizerRecipeMaker.getRecipes());
		registry.addRecipes(AnalyzerRecipeMaker.getRecipes());
		registry.addRecipes(ExtractorRecipeMaker.getRecipes());
		registry.addRecipes(AlloyerRecipeMaker.getRecipes());
		registry.addRecipes(LabOvenRecipeMaker.getRecipes());

		registry.addRecipeClickArea(GuiMineralSizer.class, 45, 55, 70, 50, RHRecipeUID.SIZER);
		registry.addRecipeClickArea(GuiMineralAnalyzer.class, 27, 42, 33, 60, RHRecipeUID.ANALYZER);
		//registry.addRecipeClickArea(GuiChemicalExtractor.class, 20, 20, 200, 30, RHRecipeUID.EXTRACTOR);
		registry.addRecipeClickArea(GuiMetalAlloyer.class, 40, 67, 130, 18, RHRecipeUID.ALLOYER);
		registry.addRecipeClickArea(GuiLabOven.class, 64, 63, 22, 59, RHRecipeUID.LAB_OVEN);

		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.mineralSizer), RHRecipeUID.SIZER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.mineralAnalyzer), RHRecipeUID.ANALYZER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.chemicalExtractor), RHRecipeUID.EXTRACTOR);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.metalAlloyer), RHRecipeUID.ALLOYER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.labOven), RHRecipeUID.LAB_OVEN);

		for(int i=0;i<EnumElement.size();i++){
			registry.addDescription(new ItemStack(ModItems.chemicalDusts,1,i), "Made in the Chemical Extractor");
		}
		
		
		registry.addDescription(new ItemStack(ModBlocks.chemicalExtractor), 
				"The chemical extractor is used to extract element dusts from shards. It needs the following to operate:\n\n "
						+ "1. A fuel source in the top left fuel slot (like coal, or wood, or whatever)\n\n"
						+ "2. Redstone dust or an induction heating element in the slot next to that. "
						+ "The induction heating element allows the block to receive rf instead of needing redstone dust\n\n"
						+ "3. Syngas in the left fluid tank, made in the Lab Oven\n\n"
						+ "4. Hydrofluoric in the right fluid tank, made in the Lab Oven\n\n"
						+ "5. A test tube in the test tube slot\n\n"
						+ "6. Lastly, any shard that has been outputted by the mineral analyzer\n\n "
						+ "Once all the ingredients are in place it will start extracting elements.\n\n"
						+ "Once a tile has extracted enough of an element to fill up a bar in the chemical table a graduated cylinder"
						+ "can be put into the test tube slot to extract the chemical dusts");
	}
}