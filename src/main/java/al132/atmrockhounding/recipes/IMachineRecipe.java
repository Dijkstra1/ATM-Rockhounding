package al132.atmrockhounding.recipes;

import java.util.List;

import net.minecraft.item.ItemStack;


public interface IMachineRecipe {

	public abstract List<ItemStack> getOutputs();
	
	public abstract List<ItemStack> getInputs();
}
