package al132.atmrockhounding.compat.crafttweaker;

import java.util.LinkedList;
import java.util.List;

import al132.atmrockhounding.compat.crafttweaker.utils.BaseListAddition;
import al132.atmrockhounding.compat.crafttweaker.utils.BaseListRemoval;
import al132.atmrockhounding.compat.crafttweaker.utils.InputHelper;
import al132.atmrockhounding.compat.crafttweaker.utils.LogHelper;
import al132.atmrockhounding.compat.crafttweaker.utils.StackHelper;
import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.recipes.machines.MineralSizerRecipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.atmrockhounding.MineralSizer")
public class CTMineralSizer {
	protected static final String name = "ATM Rockhounding Mineral Sizer";

	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack output) {
		MineTweakerAPI.apply(new Add(new MineralSizerRecipe(InputHelper.toStack(input), InputHelper.toStack(output))));
	}

	private static class Add extends BaseListAddition<MineralSizerRecipe> {
		public Add(MineralSizerRecipe recipe) {
			super(CTMineralSizer.name, ModRecipes.sizerRecipes);

			this.recipes.add(recipe);
		}

		@Override
		public String getRecipeInfo(MineralSizerRecipe recipe) {
			return LogHelper.getStackDescription(recipe.getInputs().get(0));
		}
	}

	@ZenMethod
	public static void remove(IIngredient input) {
		List<MineralSizerRecipe> recipes = new LinkedList<MineralSizerRecipe>();

		if (input == null) {
			LogHelper.logError(String.format("Required parameters missing for %s Recipe.", name));
			return;
		}

		for (MineralSizerRecipe recipe : ModRecipes.sizerRecipes) {
			if (StackHelper.matches(input, InputHelper.toIItemStack(recipe.getInputs().get(0))))
				recipes.add(recipe);
		}

		if (!recipes.isEmpty()) {
			MineTweakerAPI.apply(new Remove(recipes));
		} else {
			LogHelper.logWarning(String.format("No %s Recipe found for output %s. Command ignored!", CTMineralSizer.name,
					input.toString()));
		}

	}

	private static class Remove extends BaseListRemoval<MineralSizerRecipe> {
		public Remove(List<MineralSizerRecipe> recipes) {
			super(CTMineralSizer.name, ModRecipes.sizerRecipes, recipes);
		}

		@Override
		protected String getRecipeInfo(MineralSizerRecipe recipe) {
			return LogHelper.getStackDescription(recipe.getInputs().get(0));
		}
	}
}