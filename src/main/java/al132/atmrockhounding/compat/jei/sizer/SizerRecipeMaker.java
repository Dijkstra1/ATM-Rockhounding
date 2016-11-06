package al132.atmrockhounding.compat.jei.sizer;

import java.util.ArrayList;
import java.util.List;

import al132.atmrockhounding.blocks.ModBlocks;
import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.recipes.machines.MineralSizerRecipe;
import net.minecraft.item.ItemStack;

public class SizerRecipeMaker {

	private SizerRecipeMaker() {
	}

	public static List<SizerRecipeWrapper> getRecipes() {
		List<SizerRecipeWrapper> recipes = new ArrayList<>();
		for (MineralSizerRecipe recipe : ModRecipes.sizerRecipes) {
			recipes.add(new SizerRecipeWrapper(recipe));
		}
		ArrayList<ItemStack> mineralOutputs = new ArrayList<ItemStack>();
		for(int i = 1;i <=10;i++){
			mineralOutputs.add(new ItemStack(ModBlocks.mineralOres,1,i));
		}
		MineralSizerRecipe mineralRecipe = new MineralSizerRecipe(new ItemStack(ModBlocks.mineralOres),mineralOutputs);
		recipes.add(new SizerRecipeWrapper(mineralRecipe));
		
		return recipes;
	}

}