package al132.atmrockhounding.compat.jei.alloyer;

import javax.annotation.Nonnull;

import al132.atmrockhounding.client.gui.GuiMetalAlloyer;
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

public class AlloyerRecipeCategory extends RHRecipeCategory {

	
	private static final int OUTPUT_SLOT = 1;
	private static final int CONSUMABLE_SLOT = 2;
	private static final int INPUT_SLOT = 3;


	private final static ResourceLocation guiTexture = GuiMetalAlloyer.TEXTURE_REF;

	public AlloyerRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 40, 50, 130, 66), "jei.alloyer.name");
	}

	@Nonnull
	@Override
	public String getUid() {
		return RHRecipeUID.ALLOYER;
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		


		for(int i=0;i<6;i++){
			guiItemStacks.init(INPUT_SLOT+i, true, i*18 +12, 0);	
		}
		
		guiItemStacks.init(OUTPUT_SLOT, false, 35, 37);
		guiItemStacks.init(CONSUMABLE_SLOT, true, 57, 37);
		

		AlloyerRecipeWrapper wrapper = (AlloyerRecipeWrapper) recipeWrapper;	
		
		guiItemStacks.set(CONSUMABLE_SLOT, new ItemStack(ModItems.ingotPattern));
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
		for(int i=0; (i<6 && i < wrapper.getInputs().size()); i++){
			guiItemStacks.set(INPUT_SLOT+i, (wrapper.getInputs().get(i)));
		}
		
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		setRecipe(recipeLayout,recipeWrapper);
	}
}