package al132.atmrockhounding.recipes.machines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import al132.atmrockhounding.recipes.IMachineRecipe;
import al132.atmrockhounding.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class MetalAlloyerRecipe implements IMachineRecipe{

	private final List<ArrayList<ItemStack>> inputs;
	private final ItemStack output;

	public MetalAlloyerRecipe(ItemStack output, ArrayList<ArrayList<ItemStack>> inputs){
		this.inputs = inputs;
		this.output = output;
	}

	@SafeVarargs
	public MetalAlloyerRecipe(ItemStack output, ArrayList<ItemStack>...inputs){
		this.inputs = new ArrayList<ArrayList<ItemStack>>();
		for(int i=0; i<inputs.length; i++){
			this.inputs.add(inputs[i]);
		}
		this.output = output;
	}


	public List<ItemStack> getOutputs(){
		return new ArrayList<ItemStack>(Collections.singletonList(output));
	}

	public ArrayList<ArrayList<ItemStack>> getInputs(){
		return new ArrayList<ArrayList<ItemStack>>(inputs);
	}



	public ArrayList<ItemStack> getAllPossibleInputItems(){
		ArrayList<ItemStack> temp = new ArrayList<ItemStack>();
		for(ArrayList<ItemStack> list: this.inputs){
			for(ItemStack stack: list){
				ItemStack tempStack = stack.copy();
				temp.add(tempStack);
			}
		}

		return temp;
	}



	public boolean matches(IItemHandler dustHandler) {
		int matching = 0;

		List<ItemStack> dustStacks = Utils.handlerToStackList(dustHandler);

		for(ArrayList<ItemStack> ingredient: this.inputs){
			for(ItemStack ingredientStack: ingredient){
				if(Utils.listContainsStack(ingredientStack, dustStacks, true)) {
					matching++;
				}
			}
		}
		return matching == this.inputs.size();
	}
}