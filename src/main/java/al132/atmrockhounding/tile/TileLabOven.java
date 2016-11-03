package al132.atmrockhounding.tile;

import al132.atmrockhounding.ModConfig;
import al132.atmrockhounding.client.gui.GuiLabOven;
import al132.atmrockhounding.enums.EnumFluid;
import al132.atmrockhounding.items.ModItems;
import al132.atmrockhounding.recipes.LabOvenRecipe;
import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.tile.WrappedItemHandler.WriteMode;
import al132.atmrockhounding.utils.Utils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class TileLabOven extends TileMachine {

	public int recipeDisplayIndex;

	public static int tankMax = 100;
	private int redstoneCharge = this.getCookTimeMax();

	//Input handler slots
	public static final int SOLUTE_SLOT = 0;
	//						 FUEL_SLOT = 1;
	public static final int SOLVENT_SLOT = 2;
	public static final int OUTPUT_SLOT = 3;

	public static final int REDSTONE_SLOT = 4;

	private ItemStackHandler template = new TemplateStackHandler(1);
	private static int TEMPLATE_SLOT = 0;

	public boolean recipeScan;
	ItemStack inductor = new ItemStack(ModItems.inductionHeatingElement);

	public TileLabOven() {
		super(7, 0, 1);
		this.recipeDisplayIndex = -1;


		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == SOLUTE_SLOT && isCorrectSolute(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && Utils.isItemFuel(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SOLVENT_SLOT && isCorrectSolvent(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == REDSTONE_SLOT &&
						(insertingStack.getItem() == Items.REDSTONE || (insertingStack.isItemEqual(inductor)))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == OUTPUT_SLOT && ItemStack.areItemsEqual(insertingStack, new ItemStack(ModItems.chemicalItems,1,0)) && isValidTank(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};

		automationInput = new WrappedItemHandler(input,WriteMode.IN_OUT);
	}

	public ItemStackHandler getTemplate(){
		
		return this.template;
	}

	public int getCookTimeMax(){
		return ModConfig.speedLabOven;
	}


	private boolean isCorrectSolute(ItemStack stack) {
		if(this.recipeDisplayIndex >= 0){
			ItemStack solute = ModRecipes.labOvenRecipes.get(recipeDisplayIndex).getSolute();
			if(solute.isItemEqual(stack)){
				return true;
			}
		}
		return false;
	}

	public boolean isCorrectSolvent(ItemStack stack){
		if(this.recipeDisplayIndex >= 0){
			EnumFluid solvent = ModRecipes.labOvenRecipes.get(recipeDisplayIndex).getSolvent();
			if(stack.hasTagCompound()){
				if(solvent.getName().equals(stack.getTagCompound().getString("Fluid"))){
					return true;
				}
			}
		}
		return false;
	}

	public boolean isValidTank(ItemStack stack){
		if(this.recipeDisplayIndex >= 0){
			EnumFluid acid = ModRecipes.labOvenRecipes.get(recipeDisplayIndex).getOutput();
			if(stack.hasTagCompound()){
				if(stack.getTagCompound().getString("Fluid").equals(EnumFluid.EMPTY.getName()) || acid.getName().equals(stack.getTagCompound().getString("Fluid"))){
					return true;
				}
			}
		}
		return false;
	}

	public EnumFluid getFluidOutput(){
		for(LabOvenRecipe recipe: ModRecipes.labOvenRecipes){
			if(ItemStack.areItemsEqual(recipe.getSolute(),input.getStackInSlot(SOLUTE_SLOT))){
				return recipe.getOutput();
			}
		}
		return null;
	}


	public boolean isBurning(){
		return this.canSynthesize();
	}

	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.recipeDisplayIndex = compound.getInteger("RecipeCount");
		this.cookTime = compound.getInteger("CookTime");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("RecipeCount", this.recipeDisplayIndex);
		compound.setInteger("CookTime", this.cookTime);
		return compound;
	}


	//----------------------- PROCESS -----------------------

	@Override
	public void update(){
		if(input.getStackInSlot(FUEL_SLOT) != null){fuelHandler();}
		if(input.getStackInSlot(REDSTONE_SLOT) != null){redstoneHandler();}
		if(!worldObj.isRemote){
			if(currentRecipeIndex() >= 0 && (recipeScan || (!recipeScan && template.getStackInSlot(TEMPLATE_SLOT) == null)) ){ showIngredients(currentRecipeIndex());}
			if(canSynthesize()){
				execute();
			}
			this.markDirtyClient();
		}
	}

	private void showIngredients(int countRecipes) {
		template.setStackInSlot(TEMPLATE_SLOT, ModRecipes.labOvenRecipes.get(currentRecipeIndex()).getSolute());
		recipeScan = false;
	}

	public void resetGrid(){
		template.setStackInSlot(TEMPLATE_SLOT, null);
	}

	private void execute() {
		cookTime++;
		powerCount--;
		redstoneCount --; 
		if(cookTime >= getCookTimeMax()) {
			cookTime = 0; 
			handleOutput(getFluidOutput());
		}
		//this.markDirty();
	}

	private boolean canSynthesize() {
		if(this.recipeDisplayIndex >= 0){
			if(powerCount >= getCookTimeMax() && redstoneCount >= getCookTimeMax() ){
				EnumFluid acid = ModRecipes.labOvenRecipes.get(recipeDisplayIndex).getOutput();
				if(isTankEmpty(OUTPUT_SLOT) || (stackHasFluid(input.getStackInSlot(OUTPUT_SLOT), acid) && (!isTankFull(OUTPUT_SLOT))) ){
					EnumFluid solvent = ModRecipes.labOvenRecipes.get(recipeDisplayIndex).getSolvent();
					if(hasTank(SOLVENT_SLOT) && stackHasFluid(input.getStackInSlot(SOLVENT_SLOT),solvent) ){
						ItemStack solute = ModRecipes.labOvenRecipes.get(recipeDisplayIndex).getSolute();
						if(input.getStackInSlot(SOLUTE_SLOT) != null && solute.isItemEqual(input.getStackInSlot(SOLUTE_SLOT))){
							return true;
						}
					}
				}
			}			
		}
		return false;
	}

	private boolean isTankEmpty(int slot) {
		if(hasTank(slot)){
			if(input.getStackInSlot(slot).hasTagCompound()){
				return input.getStackInSlot(slot).getTagCompound().getString("Fluid").equals(EnumFluid.EMPTY.getName());
			}
		}
		return false;
	}

	private boolean stackHasFluid(ItemStack stack, EnumFluid fluid){
		if(stack != null){
			if(stack.hasTagCompound()){
				if(stack.getTagCompound().getString("Fluid").equals(fluid.getName())){
					return true;
				}
			}
		}
		return false;
	}

	private boolean isTankFull(int slot) {
		if(hasTank(slot)){
			if(input.getStackInSlot(slot).hasTagCompound()){
				return input.getStackInSlot(slot).getTagCompound().getInteger("Quantity") == tankMax;
			}
		}
		return false;
	}

	private boolean hasTank(int slot) {
		return  input.getStackInSlot(slot) != null
				&& input.getStackInSlot(slot).getItem() == ModItems.chemicalItems
				&& input.getStackInSlot(slot).getItemDamage() == 0 
				&& input.getStackInSlot(slot).stackSize == 1;
	}

	private void handleOutput(EnumFluid fluidOutput) {
		int quantity = 0;
		input.decrementSlot(SOLUTE_SLOT);
		input.decrementFluid(SOLVENT_SLOT);
		//add output
		quantity = input.getStackInSlot(OUTPUT_SLOT).getTagCompound().getInteger("Quantity") + 1;
		input.getStackInSlot(OUTPUT_SLOT).getTagCompound().setString("Fluid", fluidOutput.getName());
		input.getStackInSlot(OUTPUT_SLOT).getTagCompound().setInteger("Quantity", quantity);
	}


	private void redstoneHandler() {
		if(input.getStackInSlot(REDSTONE_SLOT)!= null && input.getStackInSlot(REDSTONE_SLOT).getItem() == Items.REDSTONE && redstoneCount <= (redstoneMax - redstoneCharge)){
			redstoneCount += redstoneCharge;
			input.decrementSlot(REDSTONE_SLOT);
		}
	}

	@Override
	public boolean canInduct() {
		return redstoneCount >= redstoneMax 
				&& input.getStackInSlot(REDSTONE_SLOT) != null 
				&& input.getStackInSlot(REDSTONE_SLOT).isItemEqual(inductor);
	}

	@Override
	public int getGUIHeight() {
		return GuiLabOven.HEIGHT;
	}

	public int currentRecipeIndex(){
		return recipeDisplayIndex;
	}
}