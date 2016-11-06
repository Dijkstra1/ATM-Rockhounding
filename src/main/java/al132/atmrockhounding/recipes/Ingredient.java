package al132.atmrockhounding.recipes;

import net.minecraft.item.ItemStack;

public class Ingredient {

	private Object ingredient;
	
	public Ingredient(ItemStack stack){
		this.ingredient = stack;
	}
	
	public Ingredient(OreStack stack){
		this.ingredient = stack;
	}
	
	public Ingredient(String mark, int amount) {
		this.ingredient = new OreStack(mark,amount);
	}

	public boolean hasOreStack(){
		return ingredient instanceof OreStack;
	}
	
	public boolean hasItemStack(){
		return ingredient instanceof ItemStack;
	}
	
	public OreStack getOreStack(){
			return (OreStack)ingredient;
	}
	
	public ItemStack getItemStack(){
			return (ItemStack)ingredient;
	}
}