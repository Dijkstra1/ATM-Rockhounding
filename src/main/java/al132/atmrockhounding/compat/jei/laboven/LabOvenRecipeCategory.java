package al132.atmrockhounding.compat.jei.laboven;

import javax.annotation.Nonnull;

import al132.atmrockhounding.client.gui.GuiLabOven;
import al132.atmrockhounding.compat.jei.RHRecipeCategory;
import al132.atmrockhounding.compat.jei.RHRecipeUID;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class LabOvenRecipeCategory extends RHRecipeCategory {

	private static final int INPUT_SLOT = 1;

	private final static ResourceLocation guiTexture = GuiLabOven.TEXTURE_REF;

	public LabOvenRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 43, 42, 128, 71), "jei.lab_oven.name");
	}

	@Nonnull
	@Override
	public String getUid() {
		return RHRecipeUID.LAB_OVEN;
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

		LabOvenRecipeWrapper wrapper = (LabOvenRecipeWrapper) recipeWrapper;
		
		guiFluidStacks.init(0, true, 103, 3, 20, 65, 2000, false, null);
		guiFluidStacks.init(1, false, 46, 3, 20, 65, 2000, false, null);
		
		guiFluidStacks.set(0, wrapper.getFluidInputs().get(0));
		guiFluidStacks.set(1, wrapper.getFluidOutputs().get(0));
		
		guiItemStacks.init(INPUT_SLOT, true, 2, 27);

			
		
		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
	
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		setRecipe(recipeLayout,recipeWrapper);
	}
}