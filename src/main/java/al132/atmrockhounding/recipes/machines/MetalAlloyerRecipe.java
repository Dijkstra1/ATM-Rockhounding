package al132.atmrockhounding.recipes.machines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import al132.atmrockhounding.recipes.IMachineRecipe;
import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class MetalAlloyerRecipe implements IMachineRecipe{

	private List<ArrayList<ItemStack>> inputs = new ArrayList<ArrayList<ItemStack>>();
	private ItemStack output;

	public MetalAlloyerRecipe(ItemStack output, ArrayList<ArrayList<ItemStack>> inputs){
		this.inputs = inputs;
	}

	@SafeVarargs
	public MetalAlloyerRecipe(ItemStack output, ArrayList<ItemStack>...inputs){
		for(int i=0; i<inputs.length; i++){
			this.inputs.add(inputs[i]);
		}
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



	public boolean matches(IItemHandler handler) {
		int matching = 0;

		List<ItemStack> handlerStacks = Utils.handlerToStackList(handler);

		for(ArrayList<ItemStack> ingredient: this.inputs){
			if(Utils.listContainsStackList(handlerStacks, ingredient, true)) {
				matching++;
			}
		}
		return matching == this.inputs.size();
	}
}