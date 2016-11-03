package al132.atmrockhounding.compat.jei.alloyer;

import javax.annotation.Nonnull;

import al132.atmrockhounding.compat.jei.RHRecipeUID;
import al132.atmrockhounding.recipes.MetalAlloyerRecipe;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class AlloyerRecipeHandler implements IRecipeHandler<AlloyerRecipeWrapper> {

	@Nonnull
	@Override
	public Class<AlloyerRecipeWrapper> getRecipeClass() {
		return AlloyerRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return RHRecipeUID.ALLOYER;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull AlloyerRecipeWrapper recipe) {
		return RHRecipeUID.ALLOYER;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull AlloyerRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull AlloyerRecipeWrapper wrapper) {
		MetalAlloyerRecipe recipe = wrapper.getRecipe();
		return (!(recipe.getInputs() == null || recipe.getOutputs() == null));
	}
}