package al132.atmrockhounding.compat.jei.analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import al132.atmrockhounding.compat.jei.RHRecipeWrapper;
import al132.atmrockhounding.recipes.machines.MineralAnalyzerRecipe;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

public class AnalyzerRecipeWrapper extends RHRecipeWrapper<MineralAnalyzerRecipe> {
	
	public AnalyzerRecipeWrapper(@Nonnull MineralAnalyzerRecipe recipe) {
		super(recipe);
	}
	

	@Nonnull
	@Override
	public List<ItemStack> getInputs() {
		List<ItemStack> inputs = new ArrayList<ItemStack>();
		Collections.addAll(inputs, getRecipe().getInputs().get(0));
		return inputs;
	}

	@Nonnull
	@Override
	public List<ItemStack> getOutputs() {
		return getRecipe().getOutputs();
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		// TODO Auto-generated method stub
		
	}

}