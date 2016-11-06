package al132.atmrockhounding.compat.jei.sizer;

import javax.annotation.Nonnull;

import al132.atmrockhounding.compat.jei.RHRecipeUID;
import al132.atmrockhounding.recipes.machines.MineralSizerRecipe;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class SizerRecipeHandler implements IRecipeHandler<SizerRecipeWrapper> {

	@Nonnull
	@Override
	public Class<SizerRecipeWrapper> getRecipeClass() {
		return SizerRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return RHRecipeUID.SIZER;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull SizerRecipeWrapper recipe) {
		return RHRecipeUID.SIZER;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull SizerRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull SizerRecipeWrapper wrapper) {
		MineralSizerRecipe recipe = wrapper.getRecipe();
		if (recipe.getInputs().get(0) == null) {
			return false;
		}

		if (recipe.getOutput() == null && recipe.getOutputs() == null) {
			return false;
		}
		return true;
	}
}