package al132.atmrockhounding.compat.jei.extractor;

import java.util.ArrayList;
import java.util.List;

import al132.atmrockhounding.recipes.ChemicalExtractorRecipe;
import al132.atmrockhounding.recipes.ModRecipes;

public class ExtractorRecipeMaker {

	private ExtractorRecipeMaker() {
	}

	public static List<ExtractorRecipeWrapper> getRecipes() {
		List<ExtractorRecipeWrapper> recipes = new ArrayList<>();
		for (ChemicalExtractorRecipe recipe : ModRecipes.extractorRecipes) {
			recipes.add(new ExtractorRecipeWrapper(recipe));
		}
		
		return recipes;
	}

}