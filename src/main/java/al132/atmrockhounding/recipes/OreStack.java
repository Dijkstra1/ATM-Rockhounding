package al132.atmrockhounding.recipes;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreStack {

	private int quantity;
	private String oreName;
	
	public OreStack(String oreName, int quantity){
		this.oreName = oreName;
		this.quantity = quantity;
	}
	
	public String getOreName(){
		return this.oreName;
	}
	
	public int getQuantity(){
		return this.quantity;
	}
	
	public List<ItemStack> getOres(){
		return OreDictionary.getOres(oreName);
	}
}
