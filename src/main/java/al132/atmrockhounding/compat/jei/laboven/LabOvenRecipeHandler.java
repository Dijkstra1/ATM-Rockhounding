package al132.atmrockhounding.compat.jei.laboven;

import javax.annotation.Nonnull;

import al132.atmrockhounding.compat.jei.RHRecipeUID;
import al132.atmrockhounding.recipes.machines.LabOvenRecipe;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class LabOvenRecipeHandler implements IRecipeHandler<LabOvenRecipeWrapper> {

	@Nonnull
	@Override
	public Class<LabOvenRecipeWrapper> getRecipeClass() {
		return LabOvenRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return RHRecipeUID.LAB_OVEN;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull LabOvenRecipeWrapper recipe) {
		return RHRecipeUID.LAB_OVEN;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull LabOvenRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull LabOvenRecipeWrapper wrapper) {
		LabOvenRecipe recipe = wrapper.getRecipe();
		if (recipe.getSolute() == null || recipe.getSolvent() == null || recipe.getOutput() == null) {
			return false;
		}
		return true;
	}
}