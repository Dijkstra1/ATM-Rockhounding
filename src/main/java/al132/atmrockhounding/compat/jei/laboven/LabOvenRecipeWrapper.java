package al132.atmrockhounding.compat.jei.laboven;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import al132.atmrockhounding.compat.jei.RHRecipeWrapper;
import al132.atmrockhounding.recipes.machines.LabOvenRecipe;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class LabOvenRecipeWrapper extends RHRecipeWrapper<LabOvenRecipe> {
	
	public LabOvenRecipeWrapper(@Nonnull LabOvenRecipe recipe) {
		super(recipe);
	}

	@Nonnull
	@Override
	public List<ItemStack> getInputs() {
		List<ItemStack> inputs = new ArrayList<ItemStack>();
		inputs.add(getRecipe().getSolute());
		return inputs;
	}

	@Override
	public List<FluidStack> getFluidInputs(){
		return Collections.singletonList(getRecipe().getSolvent());
	}

	@Override
	public List<FluidStack> getFluidOutputs(){
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		// TODO Auto-generated method stub
		
	}

}