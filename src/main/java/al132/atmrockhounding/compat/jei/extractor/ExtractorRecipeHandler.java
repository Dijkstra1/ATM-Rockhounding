package al132.atmrockhounding.compat.jei.extractor;

import javax.annotation.Nonnull;

import al132.atmrockhounding.compat.jei.RHRecipeUID;
import al132.atmrockhounding.recipes.machines.ChemicalExtractorRecipe;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class ExtractorRecipeHandler implements IRecipeHandler<ExtractorRecipeWrapper> {

	@Nonnull
	@Override
	public Class<ExtractorRecipeWrapper> getRecipeClass() {
		return ExtractorRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return RHRecipeUID.EXTRACTOR;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull ExtractorRecipeWrapper recipe) {
		return RHRecipeUID.EXTRACTOR;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull ExtractorRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull ExtractorRecipeWrapper wrapper) {
		ChemicalExtractorRecipe recipe = wrapper.getRecipe();
		if (recipe.getInputs() == null) {
			return false;
		}

		if (recipe.getOutputs() == null) {
			return false;
		}
		return true;
	}
}