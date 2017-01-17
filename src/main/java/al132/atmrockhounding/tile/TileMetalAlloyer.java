package al132.atmrockhounding.tile;

import java.util.ArrayList;

import al132.atmrockhounding.ModConfig;
import al132.atmrockhounding.client.gui.GuiMetalAlloyer;
import al132.atmrockhounding.items.ModItems;
import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.recipes.machines.MetalAlloyerRecipe;
import al132.atmrockhounding.tile.WrappedItemHandler.WriteMode;
import al132.atmrockhounding.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.RangedWrapper;

public class TileMetalAlloyer extends TileMachine {

	public static final int SLOT_INPUTS[] = new int[]{0,1,2,3,4,5,5};
	public static final int SLOT_CONSUMABLE = 7;

	public static final int SLOT_OUTPUT = 0;
	private MetalAlloyerRecipe currentRecipe = null;

	public static final int ENERGY_PER_TICK = 10;

	@Override
	public int getFuelIndex(){return 6;}
	@Override
	public int getInputIndex(){return -1;}

	@Override
	public int getConsumableIndex(){return 7;}


	public TileMetalAlloyer() {
		super(8, 1, 6);

		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot < SLOT_INPUTS.length && inputHasRecipe(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == getFuelIndex() && isInductorOrFuel(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SLOT_CONSUMABLE && ItemStack.areItemsEqualIgnoreDurability(insertingStack, new ItemStack(ModItems.ingotPattern))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		automationInput = new WrappedItemHandler(input,WriteMode.IN_OUT) {
			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate){
				return null;
			}
		};
	}

	public int getCookTimeMax(){
		return ModConfig.speedAlloyer;
	}

	public RangedWrapper getDustHandler(){
		return new RangedWrapper(input,0,6);
	}


	public boolean inputHasRecipe(ItemStack inStack){
		ArrayList<ItemStack> stacksToCheck = new ArrayList<ItemStack>();
		stacksToCheck.addAll(Utils.handlerToStackList(getDustHandler()));
		stacksToCheck.add(inStack);

		return ModRecipes.alloyerRecipes.stream()
				.anyMatch(recipe -> Utils.listContainsStackList(stacksToCheck, recipe.getAllPossibleInputItems(), false))

				&& Utils.handlerToStackList(getDustHandler()).stream().noneMatch(x ->ItemStack.areItemsEqual(x, inStack));
	}


	public boolean matchesRecipe(){
		this.currentRecipe = null;
		if(!Utils.isHandlerEmpty(getDustHandler())){
			for(MetalAlloyerRecipe recipe: ModRecipes.getImmutableAlloyerRecipes()){
				if(recipe.matches(getDustHandler())){
					if(recipe.getInputs().size() == Utils.countHandlerNonEmptySlots(this.getDustHandler())){
						this.currentRecipe = recipe;
						break;
					}
				}
			}
		}
		return this.currentRecipe != null;
	}

	@Override
	public void update(){
		if(!worldObj.isRemote){
			if(input.getStackInSlot(getFuelIndex()) != null){
				fuelHandler();
			}
			if(canProcess()){
				execute(); 
			}
			this.markDirtyClient();
		}
	}

	private void execute() {

		cookTime++;
		energyStorage.extractEnergy(ENERGY_PER_TICK, false);
		if(cookTime >= getCookTimeMax()) {
			cookTime = 0; 
			consumeInput();
			addOutput();
			this.currentRecipe = null;
		}
	}

	private void consumeInput(){
		for(int i=0;i<getDustHandler().getSlots();i++){
			if(getDustHandler().getStackInSlot(i) != null){
				for(ArrayList<ItemStack> ingredient: currentRecipe.getInputs()){
					for(ItemStack ingredientStack: ingredient){
						if(ItemStack.areItemsEqual(getDustHandler().getStackInSlot(i), ingredientStack)){
							for(int j=0; j<ingredientStack.stackSize;j++){
								Utils.decrementSlot(getDustHandler(), i);
							}
						}
					}
				}
			}
		}
	}

	private boolean canOutput(){
		if(this.currentRecipe == null) return false;
		if(this.output.getStackInSlot(SLOT_OUTPUT) == null) return true;
		else return (currentRecipe.getOutputs().get(0).stackSize + output.getStackInSlot(0).stackSize) <= output.getStackInSlot(0).getMaxStackSize()
				&& ItemHandlerHelper.canItemStacksStack(currentRecipe.getOutputs().get(0),output.getStackInSlot(SLOT_OUTPUT));
	}

	private void addOutput(){
		output.setOrAdd(SLOT_OUTPUT, currentRecipe.getOutputs().get(0));
	}

	@Override
	public boolean canProcess() {
		return  matchesRecipe()
				&& hasConsumable() 
				&& energyStorage.getEnergyStored() >= getCookTimeMax()
				&& canOutput()
				&& (currentRecipe.getInputs().size() == Utils.countHandlerNonEmptySlots(this.getDustHandler()));
	}

	@Override
	public int getGUIHeight() {
		return GuiMetalAlloyer.HEIGHT;
	}
}