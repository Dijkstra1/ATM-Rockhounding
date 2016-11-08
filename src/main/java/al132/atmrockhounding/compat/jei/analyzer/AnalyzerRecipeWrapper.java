package al132.atmrockhounding.compat.jei.analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import al132.atmrockhounding.compat.jei.RHRecipeWrapper;
import al132.atmrockhounding.fluids.ModFluids;
import al132.atmrockhounding.recipes.machines.MineralAnalyzerRecipe;
import al132.atmrockhounding.tile.TileMineralAnalyzer;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

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
	
	@Override
	public List<FluidStack> getFluidInputs(){
		ArrayList<FluidStack> stacks = new ArrayList<FluidStack>();
		stacks.add(new FluidStack(ModFluids.SULFURIC_ACID,TileMineralAnalyzer.consumedSulf));
		stacks.add(new FluidStack(ModFluids.HYDROCHLORIC_ACID,TileMineralAnalyzer.consumedChlo));
		stacks.add(new FluidStack(ModFluids.HYDROFLUORIC_ACID,TileMineralAnalyzer.consumedFluo));
		
		return stacks;
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