package al132.atmrockhounding.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ProbabilityStack {

	private ItemStack stack;
	private int probability;
	
	public ProbabilityStack(ItemStack stack, int probability){
		this.stack = stack;
		this.probability = probability;
	}
	
	public ProbabilityStack(Item item, int meta, int probability){
		this(new ItemStack(item,1,meta),probability);
	}
	
	public ProbabilityStack(Item item, int probability){
		this(new ItemStack(item),probability);
	}
	
	public ProbabilityStack(Block block, int meta, int probability){
		this(new ItemStack(block,1,meta),probability);
	}
	
	public ItemStack getStack(){
		return this.stack.copy();
	}
	
	public int getProbability(){
		return this.probability;
	}
}
