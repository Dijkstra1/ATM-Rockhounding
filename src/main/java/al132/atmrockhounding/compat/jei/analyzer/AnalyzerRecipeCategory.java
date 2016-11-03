package al132.atmrockhounding.compat.jei.analyzer;

import javax.annotation.Nonnull;

import al132.atmrockhounding.client.gui.GuiMineralAnalyzer;
import al132.atmrockhounding.compat.jei.RHRecipeCategory;
import al132.atmrockhounding.compat.jei.RHRecipeUID;
import al132.atmrockhounding.items.ChemicalItems;
import al132.atmrockhounding.items.ModItems;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AnalyzerRecipeCategory extends RHRecipeCategory {

	private static final int INPUT_SLOT = 1;
	private static final int OUTPUT_SLOT = 2;
	private static final int TUBE_SLOT = 3;
	private static final int SULFURIC_SLOT = 4;
	private static final int CHLOR_SLOT = 5;
	private static final int FLUO_SLOT = 6;
	


	private final static ResourceLocation guiTexture = GuiMineralAnalyzer.TEXTURE_REF;

	public AnalyzerRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 40, 16, 130, 75), "jei.Analyzer.name");
	}

	@Nonnull
	@Override
	public String getUid() {
		return RHRecipeUID.ANALYZER;
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(INPUT_SLOT, true, 13, 7);
		guiItemStacks.init(OUTPUT_SLOT, false, 49, 55);
		guiItemStacks.init(TUBE_SLOT, true, 49, 29);
		guiItemStacks.init(SULFURIC_SLOT, true, 103, 7);
		guiItemStacks.init(CHLOR_SLOT, true, 103, 29);
		guiItemStacks.init(FLUO_SLOT, true, 103, 51);
		

		AnalyzerRecipeWrapper wrapper = (AnalyzerRecipeWrapper) recipeWrapper;	
		
		guiItemStacks.set(TUBE_SLOT, new ItemStack(ModItems.testTube));
		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
	
		guiItemStacks.set(SULFURIC_SLOT, ChemicalItems.makeTanks(new ItemStack(ModItems.chemicalItems), "Sulfuric Acid", 100));
		guiItemStacks.set(CHLOR_SLOT, ChemicalItems.makeTanks(new ItemStack(ModItems.chemicalItems), "Hydrochloric Acid", 100));
		guiItemStacks.set(FLUO_SLOT, ChemicalItems.makeTanks(new ItemStack(ModItems.chemicalItems), "Hydrofluoric Acid", 100));
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		setRecipe(recipeLayout,recipeWrapper);
	}
}