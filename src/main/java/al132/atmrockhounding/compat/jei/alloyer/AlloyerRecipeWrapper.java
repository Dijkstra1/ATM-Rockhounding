package al132.atmrockhounding.compat.jei.alloyer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import al132.atmrockhounding.compat.jei.RHRecipeWrapper;
import al132.atmrockhounding.recipes.machines.MetalAlloyerRecipe;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

public class AlloyerRecipeWrapper extends RHRecipeWrapper<MetalAlloyerRecipe> {
	

	public AlloyerRecipeWrapper(@Nonnull MetalAlloyerRecipe recipe) {
		super(recipe);
	}

	@Nonnull
	@Override
	public ArrayList<ArrayList<ItemStack>> getInputs() {
		return getRecipe().getInputs();
	}

	@Nonnull
	@Override
	public List<ItemStack> getOutputs() {
		return Collections.singletonList(getRecipe().getOutputs().get(0));
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
	}

}