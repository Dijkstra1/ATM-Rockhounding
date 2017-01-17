package al132.atmrockhounding.compat.jei.sizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import al132.atmrockhounding.compat.jei.RHRecipeWrapper;
import al132.atmrockhounding.recipes.machines.MineralSizerRecipe;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

public class SizerRecipeWrapper extends RHRecipeWrapper<MineralSizerRecipe> {
	
	public SizerRecipeWrapper(@Nonnull MineralSizerRecipe recipe) {
		super(recipe);
	}

	@Nonnull
	@Override
	public List<ItemStack> getInputs() {
		List<ItemStack> inputs = new ArrayList<ItemStack>();
		inputs.addAll(getRecipe().getInputs());
		return inputs;
	}

	@Nonnull
	@Override
	public List<ItemStack> getOutputs() {
		if(getRecipe().getOutputs() != null) return getRecipe().getOutputs();
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		
		
	}

}