package al132.atmrockhounding.compat.jei.laboven;

import java.util.ArrayList;
import java.util.List;

import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.recipes.machines.LabOvenRecipe;

public class LabOvenRecipeMaker {

	private LabOvenRecipeMaker() {
	}

	public static List<LabOvenRecipeWrapper> getRecipes() {
		List<LabOvenRecipeWrapper> recipes = new ArrayList<>();
		for (LabOvenRecipe recipe : ModRecipes.labOvenRecipes) {
			recipes.add(new LabOvenRecipeWrapper(recipe));
		}
		return recipes;
	}

}