package al132.atmrockhounding.compat.jei.analyzer;

import javax.annotation.Nonnull;

import al132.atmrockhounding.client.gui.GuiMineralAnalyzer;
import al132.atmrockhounding.compat.jei.RHRecipeCategory;
import al132.atmrockhounding.compat.jei.RHRecipeUID;
import al132.atmrockhounding.fluids.ModFluids;
import al132.atmrockhounding.items.ModItems;
import al132.atmrockhounding.tile.TileMineralAnalyzer;
import al132.atmrockhounding.utils.RenderUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class AnalyzerRecipeCategory extends RHRecipeCategory {

	private static final int INPUT_SLOT = 1;
	private static final int OUTPUT_SLOT = 2;
	private static final int TUBE_SLOT = 3;
	private static final int SULFURIC_SLOT = 4;
	private static final int CHLOR_SLOT = 5;
	private static final int FLUO_SLOT = 6;
	


	private final static ResourceLocation guiTexture = GuiMineralAnalyzer.TEXTURE_REF;

	public AnalyzerRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 26, 16, 143, 85), "jei.Analyzer.name");
	}

	@Nonnull
	@Override
	public String getUid() {
		return RHRecipeUID.ANALYZER;
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {

		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		guiFluidStacks.init(0, true, 79, 20, 16, 60, 100, false, null);
		guiFluidStacks.init(1, true, 101,20, 16, 60, 100, false, null);
		guiFluidStacks.init(2, true, 123,20, 16, 60, 100, false, null);
		
		guiFluidStacks.set(0, new FluidStack(ModFluids.SULFURIC_ACID,TileMineralAnalyzer.consumedSulf));
		guiFluidStacks.set(1, new FluidStack(ModFluids.HYDROCHLORIC_ACID,TileMineralAnalyzer.consumedChlo));
		guiFluidStacks.set(2, new FluidStack(ModFluids.HYDROFLUORIC_ACID,TileMineralAnalyzer.consumedFluo));
		
		
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(INPUT_SLOT, true, 7, 7);
		guiItemStacks.init(OUTPUT_SLOT, false, 43, 55);
		guiItemStacks.init(TUBE_SLOT, true, 43, 29);

		AnalyzerRecipeWrapper wrapper = (AnalyzerRecipeWrapper) recipeWrapper;	
		
		guiItemStacks.set(TUBE_SLOT, new ItemStack(ModItems.testTube));
		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		setRecipe(recipeLayout,recipeWrapper);
	}
}