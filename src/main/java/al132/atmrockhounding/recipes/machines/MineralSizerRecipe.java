package al132.atmrockhounding.recipes.machines;

import java.util.ArrayList;
import java.util.List;

import al132.atmrockhounding.recipes.IMachineRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MineralSizerRecipe implements IMachineRecipe {

	private ItemStack input;
	private ItemStack output;

	//=======
	//FOR JEI MINERAL RECIPE ONLY
	private List<ItemStack> outputs;

	public MineralSizerRecipe(ItemStack input, List<ItemStack> outputs){
		this.input = input;
		this.outputs = outputs;
	}
	//=====


	public MineralSizerRecipe(Item input, ItemStack output){
		this(new ItemStack(input),output);
	}

	public MineralSizerRecipe(Item input, int inputMeta, Item output, int outputMeta){
		this(new ItemStack(input,1,inputMeta),new ItemStack(output,1,outputMeta));
	}



	public MineralSizerRecipe(ItemStack input, ItemStack output){
		this.input = input;
		this.output = output;
	}


	public MineralSizerRecipe(Block inputBlock, ItemStack output) {
		this(new ItemStack(inputBlock), output);
	}

	public MineralSizerRecipe(Block input, int inputMeta, Item output, int outputMeta) {
		this(new ItemStack(input,1,inputMeta),new ItemStack(output,1,outputMeta));
	}

	public ArrayList<ItemStack> getInputs() {
		ArrayList<ItemStack> temp = new ArrayList<ItemStack>();
		if(input != null){
			temp.add(input);
		}
		return temp;
	}


	public ItemStack getOutput() {
		if(output != null) return output.copy();
		else return null;
	}

	public List<ItemStack> getOutputs(){
		return this.outputs;
	}
}