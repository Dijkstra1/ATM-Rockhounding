package al132.atmrockhounding.compat.jei.analyzer;

import javax.annotation.Nonnull;

import al132.atmrockhounding.compat.jei.RHRecipeUID;
import al132.atmrockhounding.recipes.machines.MineralAnalyzerRecipe;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class AnalyzerRecipeHandler implements IRecipeHandler<AnalyzerRecipeWrapper> {

	@Nonnull
	@Override
	public Class<AnalyzerRecipeWrapper> getRecipeClass() {
		return AnalyzerRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return RHRecipeUID.ANALYZER;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull AnalyzerRecipeWrapper recipe) {
		return RHRecipeUID.ANALYZER;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull AnalyzerRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull AnalyzerRecipeWrapper wrapper) {
		MineralAnalyzerRecipe recipe = wrapper.getRecipe();
		if (recipe.getInputs() == null) {
			return false;
		}

		if (recipe.getOutputs() == null) {
			return false;
		}
		return true;
	}
}