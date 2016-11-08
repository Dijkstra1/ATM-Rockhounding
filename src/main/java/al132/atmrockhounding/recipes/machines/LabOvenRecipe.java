package al132.atmrockhounding.recipes.machines;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class LabOvenRecipe {

	private final ItemStack solute;
	private final FluidStack solvent;
	private final FluidStack output;
	
	public LabOvenRecipe(ItemStack solute, FluidStack solvent, FluidStack output){
		this.solute = solute;
		this.solvent = solvent;
		this.output = output;
	}

	
	public ItemStack getSolute(){
		return this.solute.copy();
	}
	
	public FluidStack getSolvent(){
		return this.solvent;
	}
	
	public FluidStack getOutput(){
		return this.output;
	}
}
