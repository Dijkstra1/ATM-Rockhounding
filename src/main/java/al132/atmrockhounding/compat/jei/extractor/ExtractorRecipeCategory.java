package al132.atmrockhounding.compat.jei.extractor;

import javax.annotation.Nonnull;

import al132.atmrockhounding.client.gui.GuiChemicalExtractor;
import al132.atmrockhounding.compat.jei.RHRecipeCategory;
import al132.atmrockhounding.compat.jei.RHRecipeUID;
import al132.atmrockhounding.items.ModItems;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ExtractorRecipeCategory extends RHRecipeCategory {

	private static final int INPUT_SLOT = 1;
	private static final int OUTPUT_SLOT = 2;
	private static final int GEAR_SLOT = 3;


	private final static ResourceLocation guiTexture = GuiChemicalExtractor.TEXTURE_REF;

	public ExtractorRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 40, 16, 130, 75), "jei.extractor.name");
	}

	@Nonnull
	@Override
	public String getUid() {
		return RHRecipeUID.EXTRACTOR;
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		


		guiItemStacks.init(INPUT_SLOT, true, 3, 11);
		guiItemStacks.init(OUTPUT_SLOT, false, 79, 31);
		guiItemStacks.init(GEAR_SLOT, true, 40, 31);
		

		ExtractorRecipeWrapper wrapper = (ExtractorRecipeWrapper) recipeWrapper;	
		
		guiItemStacks.set(GEAR_SLOT, new ItemStack(ModItems.gear));
		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
	
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		setRecipe(recipeLayout,recipeWrapper);
	}
}