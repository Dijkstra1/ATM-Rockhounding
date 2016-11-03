package al132.atmrockhounding.recipes;

import java.util.ArrayList;

import al132.atmrockhounding.items.ModItems;
import net.minecraft.item.ItemStack;

public class MetalAlloyerRecipe implements IMachineRecipe{

	private ArrayList<ItemStack> input = new ArrayList<ItemStack>();
	private ItemStack output;
	
	
	public MetalAlloyerRecipe(ItemStack output, ItemStack...input){
		for(ItemStack stack: input){
			this.input.add(stack);
		}
		this.output = output;
	}

	
	public MetalAlloyerRecipe(ItemStack output, int...input){
		this.output = output;
		for(int i=0; i< input.length; i+=2){
			this.input.add(new ItemStack(ModItems.chemicalDusts,input[i],input[i+1]));
		}
	}
	
	public ArrayList<ItemStack> getInputs(){
		return new ArrayList<ItemStack>(this.input);
	}
	
	public ArrayList<ItemStack> getOutputs(){
		ArrayList<ItemStack> temp = new ArrayList<ItemStack>();
		temp.add(this.output);
		return temp;
	}
}