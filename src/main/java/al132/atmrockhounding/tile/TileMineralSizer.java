package al132.atmrockhounding.tile;


import java.util.ArrayList;

import al132.atmrockhounding.ModConfig;
import al132.atmrockhounding.blocks.ModBlocks;
import al132.atmrockhounding.client.gui.GuiMineralSizer;
import al132.atmrockhounding.items.ModItems;
import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.recipes.machines.MineralSizerRecipe;
import al132.atmrockhounding.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreDictionary;

public class TileMineralSizer extends TileMachine {

	public static final int ENERGY_PER_TICK = 10;

	public static final int OUTPUT_SLOT = 0;
	public static final int GOOD_SLOT = 1;
	public static final int WASTE_SLOT = 2;

	@Override
	public int getInputIndex(){return 0;}

	@Override
	public int getFuelIndex(){return 1;}

	@Override
	public int getConsumableIndex(){return 2;}


	public TileMineralSizer() {
		super(3,3,1);
		this.cookTime = 0;


		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				//input slots
				if(slot == getInputIndex() && (hasRecipe(insertingStack) || isIngotOredicted(insertingStack)) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == getFuelIndex() && isInductorOrFuel(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == getConsumableIndex() && Utils.areItemsEqualIgnoreMeta(new ItemStack(ModItems.gear), insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};

		this.automationInput = new WrappedItemHandler(input, WrappedItemHandler.WriteMode.IN_OUT){
			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate){
				return null;
			}
		};
	}

	@Override
	public int getGUIHeight() {
		return GuiMineralSizer.HEIGHT;
	}

	//----------------------- CUSTOM -----------------------

	public int getMaxCookTime(){
		return ModConfig.speedSizer;
	}


	private boolean canOutput(ItemStack stack){

		if(ItemStack.areItemsEqual(new ItemStack(ModBlocks.mineralOres,1,0), stack)){
			if(Utils.isHandlerEmpty(output)){
				return true;
			}
			/*else if(( output.getStackInSlot(OUTPUT_SLOT) == null || output.getStackInSlot(OUTPUT_SLOT).stackSize <= 60)
					&&( output.getStackInSlot(GOOD_SLOT) == null ||output.getStackInSlot(GOOD_SLOT).stackSize <= 62)
					&& (output.getStackInSlot(WASTE_SLOT) == null|| output.getStackInSlot(WASTE_SLOT).stackSize <=63)){
				return true;

			}*/
		}
		else if(stack != null) {
			if(output.getStackInSlot(OUTPUT_SLOT) != null){
				ItemStack recipeOutput = getRecipeOutput(new ItemStack(stack.getItem(),1,stack.getItemDamage()));
				return (recipeOutput.stackSize + output.getStackInSlot(OUTPUT_SLOT).stackSize <= 64)
						&& ItemHandlerHelper.canItemStacksStack(output.getStackInSlot(OUTPUT_SLOT), recipeOutput);
			}
			else{
				return true;
			}
		}
		return false;
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
		return getRecipeOutput(input.getStackInSlot(getInputIndex()));
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


	//----------------------- PROCESS -----------------------

	@Override
	public void update(){
		if(input.getStackInSlot(getFuelIndex()) != null){fuelHandler();}
		if(!worldObj.isRemote){
			if(canProcess()){
				cookTime++;
				energyStorage.extractEnergy(ENERGY_PER_TICK, false);
				if(cookTime >= getMaxCookTime()) {
					cookTime = 0;
					process();
				}
			}
			this.markDirtyClient();
		}
	}

	@Override
	public boolean canProcess() {
		ItemStack stack = input.getStackInSlot(getInputIndex());
		return  (canOutput(stack)
				&& (hasRecipe(stack) || isIngotOredicted(stack))
				&& hasConsumable()
				&& energyStorage.getEnergyStored() >= getMaxCookTime());
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

		input.decrementSlot(getInputIndex());
		input.damageSlot(getConsumableIndex());
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
			}
		}
		return false;
	}
}