package al132.atmrockhounding.recipes.machines;

import java.util.ArrayList;
import java.util.List;

import al132.atmrockhounding.items.ModItems;
import al132.atmrockhounding.recipes.ElementStack;
import al132.atmrockhounding.recipes.IMachineRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ChemicalExtractorRecipe implements IMachineRecipe {

	List<ItemStack> inputs = new ArrayList<ItemStack>();
	List<ElementStack> outputs = new ArrayList<ElementStack>();

	public ChemicalExtractorRecipe(ItemStack input, List<ElementStack> outputs){
		this.inputs.add(input);
		this.outputs = outputs;
	}

	public ChemicalExtractorRecipe(Item item, int meta, ElementStack... outputs){
		
		this.inputs.add(new ItemStack(item,1,meta));
		for(int i=0; i < outputs.length; i ++){
			this.outputs.add(outputs[i]);
		}
	}

	public List<ItemStack> getInputs(){
		return inputs;
	}

	public List<ItemStack> getOutputs(){
		ArrayList<ItemStack> temp = new ArrayList<ItemStack>();
		for(ElementStack stack: outputs){
			temp.add(new ItemStack(ModItems.chemicalDusts,1,stack.getElement().ordinal()));
		}
		return temp;
	}

	public List<ElementStack> getOutputElements(){
		return outputs;
	}


}
