package al132.atmrockhounding.recipes;

import al132.atmrockhounding.blocks.ModBlocks;
import al132.atmrockhounding.enums.EnumAlloy;
import al132.atmrockhounding.enums.EnumElement;
import al132.atmrockhounding.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModDictionary {

	public static void loadDictionary()  {
		OreDictionary.registerOre("dustSalt", new ItemStack(ModItems.chemicalItems, 1, 1));	
		OreDictionary.registerOre("itemFluorite", new ItemStack(ModItems.halideShards, 1, 4));	
		OreDictionary.registerOre("itemPyrite", new ItemStack(ModItems.sulfideShards, 1, 6));	

		for(int x = 0; x < EnumElement.size(); x++){
			OreDictionary.registerOre(EnumElement.getDustName(x), new ItemStack(ModItems.chemicalDusts, 1, x));	
		}

		for(int x = 0; x < EnumAlloy.size(); x++){
			OreDictionary.registerOre(EnumAlloy.getDictName("blocks", x), new ItemStack(ModBlocks.alloyBlocks, 1, x));
			OreDictionary.registerOre(EnumAlloy.getDictName("dust",x), new ItemStack(ModItems.alloyDusts, 1, x));	
			OreDictionary.registerOre(EnumAlloy.getDictName("ingot",x), new ItemStack(ModItems.alloyIngots, 1, x));	
			OreDictionary.registerOre(EnumAlloy.getDictName("nugget",x), new ItemStack(ModItems.alloyNuggets, 1, x));	
		}
	}
}