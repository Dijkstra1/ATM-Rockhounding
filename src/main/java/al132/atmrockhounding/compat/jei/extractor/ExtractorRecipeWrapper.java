package al132.atmrockhounding.compat.jei.extractor;

import java.util.List;

import javax.annotation.Nonnull;

import al132.atmrockhounding.compat.jei.RHRecipeWrapper;
import al132.atmrockhounding.recipes.ChemicalExtractorRecipe;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

public class ExtractorRecipeWrapper extends RHRecipeWrapper<ChemicalExtractorRecipe> {
	
	public ExtractorRecipeWrapper(@Nonnull ChemicalExtractorRecipe recipe) {
		super(recipe);
	}

	@Nonnull
	@Override
	public List<ItemStack> getInputs() {
		return getRecipe().getInputs();
	}

	@Nonnull
	@Override
	public List<ItemStack> getOutputs() {
		return getRecipe().getOutputs();
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
	}

}