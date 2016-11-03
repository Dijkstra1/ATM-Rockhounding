package al132.atmrockhounding.compat.jei.alloyer;

import java.util.ArrayList;
import java.util.List;

import al132.atmrockhounding.recipes.MetalAlloyerRecipe;
import al132.atmrockhounding.recipes.ModRecipes;

public class AlloyerRecipeMaker {

	private AlloyerRecipeMaker() {}

	public static List<AlloyerRecipeWrapper> getRecipes() {
		List<AlloyerRecipeWrapper> recipes = new ArrayList<>();
		for (MetalAlloyerRecipe recipe : ModRecipes.alloyerRecipes) {
			recipes.add(new AlloyerRecipeWrapper(recipe));
		}
		return recipes;
	}
}