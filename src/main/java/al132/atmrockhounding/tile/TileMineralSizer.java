package al132.atmrockhounding.tile;


import java.util.ArrayList;

import al132.atmrockhounding.ModConfig;
import al132.atmrockhounding.Reference;
import al132.atmrockhounding.blocks.ModBlocks;
import al132.atmrockhounding.client.gui.GuiMineralSizer;
import al132.atmrockhounding.items.ModItems;
import al132.atmrockhounding.recipes.MineralSizerRecipe;
import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreDictionary;

public class TileMineralSizer extends TileMachine {

	public static final int INPUT_SLOT = 0;
	//						FUEL_SLOT = 1;
	public static final int GEAR_SLOT = 2;
	public static final int INDUCTOR_SLOT = 3;

	public static final int OUTPUT_SLOT = 0;
	public static final int GOOD_SLOT = 1;
	public static final int WASTE_SLOT = 2;

	public TileMineralSizer() {
		super(4,3,1);
		this.powerCount = 0;
		this.cookTime = 0;
		
		
		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				//input slots
				if(slot == 0 && (hasRecipe(insertingStack) || isIngotOredicted(insertingStack)) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == 1 && Utils.isItemFuel(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == 2 && Utils.areItemsEqualIgnoreMeta(new ItemStack(ModItems.gear), insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == 3 && ItemStack.areItemsEqual(insertingStack, Reference.inductor)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};

		this.automationInput = new WrappedItemHandler(input, WrappedItemHandler.WriteMode.IN_OUT);
	}

	@Override
	public int getGUIHeight() {
		return GuiMineralSizer.HEIGHT;
	}

	//----------------------- CUSTOM -----------------------

	public int getMaxCookTime(){
		return ModConfig.speedSizer;
	}

	private boolean allOutputsEmpty(){
		return (output.getStackInSlot(OUTPUT_SLOT) == null
				&& output.getStackInSlot(GOOD_SLOT) == null
				&& output.getStackInSlot(WASTE_SLOT) == null);
	}

	private boolean canOutput(ItemStack stack){

		if(ItemStack.areItemsEqual(new ItemStack(ModBlocks.mineralOres,1,0), stack)
				&& allOutputsEmpty()){
			return true;
		}
		else{
			if(stack != null && output.getStackInSlot(OUTPUT_SLOT) != null){
				ItemStack recipeOutput = getRecipeOutput(new ItemStack(stack.getItem(),1,stack.getItemDamage()));
				return ItemHandlerHelper.canItemStacksStack(output.getStackInSlot(OUTPUT_SLOT), recipeOutput);
			}
			else return true;
		}
	}

	public boolean hasGear(){
		return !(input.getStackInSlot(GEAR_SLOT) == null);
	}

	@Override
	public boolean canInduct(){
		return !(input.getStackInSlot(INDUCTOR_SLOT) == null);
	}


	public boolean hasRecipe(ItemStack stack){
		if(stack != null){
			for(MineralSizerRecipe recipe: ModRecipes.sizerRecipes){
				if(ItemStack.areItemsEqual(recipe.getInputs().get(0), stack)) return true;
			}
		}
		return false;
	}

	public ItemStack getRecipeOutput(){
		return getRecipeOutput(input.getStackInSlot(INPUT_SLOT));
	}


	public static ItemStack getRecipeOutput(ItemStack inputStack){
		if(inputStack != null){
			for(MineralSizerRecipe recipe: ModRecipes.sizerRecipes){
				if(ItemStack.areItemsEqual(recipe.getInputs().get(0), inputStack)){
					return recipe.getOutput();
				}
			}
		}
		return null;
	}


	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.cookTime = compound.getInteger("CookTime");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("CookTime", this.cookTime);
		return compound;
	}



	//----------------------- PROCESS -----------------------

	@Override
	public void update(){
		if(input.getStackInSlot(FUEL_SLOT) != null){fuelHandler();}
		if(!worldObj.isRemote){
			if(canProcess(input.getStackInSlot(INPUT_SLOT))){
				System.out.println("cook time is: " + cookTime);
				cookTime++;
				powerCount--;
				if(cookTime >= getMaxCookTime()) {
					cookTime = 0;
					System.out.println("Processing, get max cook is " + getMaxCookTime());
					process();
				}
			}
			this.markDirtyClient();
		}
	}

	private boolean canProcess(ItemStack stack) {
		return  (canOutput(stack)
				&& (hasRecipe(stack) || isIngotOredicted(stack))
				&& hasGear()
				&& powerCount >= getMaxCookTime());
	}

	private void process() {
		ItemStack recipeOutput = getRecipeOutput();
		//Special case for unidentified minerals
		if(recipeOutput == null){
			//calculate primary output
			ItemStack primaryStack = new ItemStack(ModBlocks.mineralOres, (rand.nextInt(4) + 1), extractCategory());
			output.setStackInSlot(OUTPUT_SLOT,primaryStack);
			//calculate secondary output
			if(rand.nextInt(100) < 25){
				ItemStack secondaryStack = new ItemStack(ModBlocks.mineralOres, (rand.nextInt(2) + 1), extractCategory());
				output.setStackInSlot(GOOD_SLOT,secondaryStack);
			}
			//calculate waste output
			if(rand.nextInt(100) < 5){
				ItemStack wasteStack = new ItemStack(ModBlocks.mineralOres, 1, extractCategory());
				output.setStackInSlot(WASTE_SLOT,wasteStack);
			}
		}
		else{ //All other cases
			if(output.internalInsertion(OUTPUT_SLOT, recipeOutput, true) == recipeOutput){
				return;
			}
			else{
				output.internalInsertion(OUTPUT_SLOT, recipeOutput, false);

			}
		}

		input.decrementSlot(INPUT_SLOT);
		input.damageSlot(GEAR_SLOT);
		this.markDirty();
	}

	private int extractCategory() {	
		Integer getCategory = rand.nextInt(158);
		if(getCategory < 5){ return 1;										//arsenate
		}else if(getCategory >= 5   && getCategory < 17){  return 2;		//borate
		}else if(getCategory >= 17  && getCategory < 24){  return 3;		//carbonate
		}else if(getCategory >= 24  && getCategory < 33){  return 4;		//halide
		}else if(getCategory >= 33  && getCategory < 53){  return 5;		//native
		}else if(getCategory >= 53  && getCategory < 85){  return 6;		//oxide
		}else if(getCategory >= 85  && getCategory < 103){ return 7;		//phosphate
		}else if(getCategory >= 103  && getCategory < 119){return 8;		//silicate
		}else if(getCategory >= 119 && getCategory < 130){ return 9;		//sulfate
		}else if(getCategory >= 130 ){ return 10;							//sulfide
		} return 0;
	}

	private boolean isIngotOredicted(ItemStack stack) {
		if(stack != null){
			ArrayList<Integer> inputOreIDs = Utils.intArrayToList(OreDictionary.getOreIDs(stack));
			for(MineralSizerRecipe recipe: ModRecipes.sizerRecipes){
				ArrayList<Integer> recipeOreIDs = Utils.intArrayToList(OreDictionary.getOreIDs(recipe.getInputs().get(0)));
				for(Integer oreID: recipeOreIDs){
					if(inputOreIDs.contains(oreID)) return true;
				}
				//if(oreName != null && oreName.contains("ingot")){
			}
		}
		return false;
	}
}