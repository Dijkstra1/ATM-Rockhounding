package al132.atmrockhounding.recipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import al132.atmrockhounding.utils.ProbabilityStack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MineralAnalyzerRecipe implements IMachineRecipe {

	private ItemStack input;
	private ArrayList<ProbabilityStack> probabilityOutputs;

	public MineralAnalyzerRecipe(ItemStack input, ArrayList<ProbabilityStack> outputs){
		this.input = input;
		this.probabilityOutputs = outputs;
	}

	public MineralAnalyzerRecipe(Block input, int inputMeta, Item output, int[] probabilities){
		this.input = new ItemStack(input,1,inputMeta);
		this.probabilityOutputs = new ArrayList<ProbabilityStack>();
		for(int i=0; i<probabilities.length; i++){
			this.probabilityOutputs.add(new ProbabilityStack(new ItemStack(output,1,i),probabilities[i]));
		}
	}

	public List<ItemStack> getInputs(){
		return Collections.singletonList(input);
	}

	public ArrayList<ProbabilityStack> getProbabilityStack(){
		return this.probabilityOutputs;
	}

	public ArrayList<ItemStack> getOutputs(){
		ArrayList<ItemStack> temp = new ArrayList<ItemStack>();
		probabilityOutputs.forEach(x -> temp.add(x.getStack()));
		return temp;
	}
}