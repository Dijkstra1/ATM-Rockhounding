package al132.atmrockhounding.compat.crafttweaker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import al132.atmrockhounding.compat.crafttweaker.utils.BaseListAddition;
import al132.atmrockhounding.compat.crafttweaker.utils.BaseListRemoval;
import al132.atmrockhounding.compat.crafttweaker.utils.InputHelper;
import al132.atmrockhounding.compat.crafttweaker.utils.LogHelper;
import al132.atmrockhounding.compat.crafttweaker.utils.StackHelper;
import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.recipes.machines.MetalAlloyerRecipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.atmrockhounding.MetalAlloyer")
public class CTMetalAlloyer {
	protected static final String name = "ATM Rockhounding Metal Alloyer";

	@ZenMethod
	public static void addRecipe(IItemStack output1, IIngredient input1,
			@Optional IIngredient input2, @Optional IIngredient input3, @Optional IIngredient input4,
			@Optional IIngredient input5, @Optional IIngredient input6) {

		ArrayList<IIngredient> ingredientList = new ArrayList<IIngredient>();
		ingredientList.add(input1);
		ingredientList.add(input2);
		ingredientList.add(input3);
		ingredientList.add(input4);
		ingredientList.add(input5);
		ingredientList.add(input6);

		ArrayList<ArrayList<ItemStack>> inputList = new ArrayList<ArrayList<ItemStack>>();

		for(IIngredient ingredient: ingredientList){
			if(ingredient != null){
				
				ArrayList<ItemStack> singleInput = new ArrayList<ItemStack>();
				for(IItemStack ii: ingredient.getItems()){
					ItemStack tempstack = InputHelper.toStack(ii);
					tempstack.stackSize = ingredient.getAmount();
					singleInput.add(tempstack);
				}
				inputList.add(singleInput);
			}
		}


		MineTweakerAPI.apply(new Add(new MetalAlloyerRecipe(InputHelper.toStack(output1),inputList)));
	}

	private static class Add extends BaseListAddition<MetalAlloyerRecipe> {
		public Add(MetalAlloyerRecipe recipe) {
			super(CTMetalAlloyer.name, ModRecipes.alloyerRecipes);

			this.recipes.add(recipe);
		}

		@Override
		public String getRecipeInfo(MetalAlloyerRecipe recipe) {
			return LogHelper.getStackDescription(recipe.getOutputs().get(0));
		}
	}

	@ZenMethod
	public static void remove(IIngredient output1, IIngredient output2) {
		List<MetalAlloyerRecipe> recipes = new LinkedList<MetalAlloyerRecipe>();

		if (output1 == null) {
			LogHelper.logError(String.format("Required parameters missing for %s Recipe.", name));
			return;
		}

		for (MetalAlloyerRecipe recipe : ModRecipes.alloyerRecipes) {
			if (StackHelper.matches(output1, InputHelper.toIItemStack(recipe.getOutputs().get(0))))
				recipes.add(recipe);
		}

		if (!recipes.isEmpty()) {
			MineTweakerAPI.apply(new Remove(recipes));
		} else {
			LogHelper.logWarning(String.format("No %s Recipe found for output %s. Command ignored!", CTMetalAlloyer.name,
					output1.toString()));
		}

	}

	private static class Remove extends BaseListRemoval<MetalAlloyerRecipe> {
		public Remove(List<MetalAlloyerRecipe> recipes) {
			super(CTMetalAlloyer.name, ModRecipes.alloyerRecipes, recipes);
		}

		@Override
		protected String getRecipeInfo(MetalAlloyerRecipe recipe) {
			return LogHelper.getStackDescription(recipe.getOutputs().get(0));
		}
	}
}