package al132.atmrockhounding.compat.jei;

import al132.atmrockhounding.blocks.ModBlocks;
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
import al132.atmrockhounding.compat.jei.sizer.SizerRecipeCategory;
import al132.atmrockhounding.compat.jei.sizer.SizerRecipeHandler;
import al132.atmrockhounding.compat.jei.sizer.SizerRecipeMaker;
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
				new AlloyerRecipeCategory(guiHelper)
				);
		
		registry.addRecipeHandlers(
				new SizerRecipeHandler(),
				new AnalyzerRecipeHandler(),
				new ExtractorRecipeHandler(),
				new AlloyerRecipeHandler()
				);
		
		registry.addRecipes(SizerRecipeMaker.getRecipes());
		registry.addRecipes(AnalyzerRecipeMaker.getRecipes());
		registry.addRecipes(ExtractorRecipeMaker.getRecipes());
		registry.addRecipes(AlloyerRecipeMaker.getRecipes());
		
		registry.addRecipeClickArea(GuiMineralSizer.class, 45, 55, 70, 50, RHRecipeUID.SIZER);
		registry.addRecipeClickArea(GuiMineralAnalyzer.class, 39, 30, 35, 75, RHRecipeUID.ANALYZER);
		//registry.addRecipeClickArea(GuiChemicalExtractor.class, 20, 20, 200, 30, RHRecipeUID.EXTRACTOR);
		registry.addRecipeClickArea(GuiMetalAlloyer.class, 40, 67, 130, 18, RHRecipeUID.ALLOYER);
		
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.mineralSizer), RHRecipeUID.SIZER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.mineralAnalyzer), RHRecipeUID.ANALYZER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.chemicalExtractor), RHRecipeUID.EXTRACTOR);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.metalAlloyer), RHRecipeUID.ALLOYER);

		
	}
}