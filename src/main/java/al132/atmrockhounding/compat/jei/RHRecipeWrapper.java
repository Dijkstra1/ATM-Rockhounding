package al132.atmrockhounding.compat.jei;

import javax.annotation.Nonnull;

import mezz.jei.api.recipe.BlankRecipeWrapper;

public abstract class RHRecipeWrapper<R> extends BlankRecipeWrapper {

	@Nonnull
	private final R recipe;

	public RHRecipeWrapper(@Nonnull R recipe) {
		this.recipe = recipe;
	}

	@Nonnull
	public R getRecipe() {
		return recipe;
	}
}