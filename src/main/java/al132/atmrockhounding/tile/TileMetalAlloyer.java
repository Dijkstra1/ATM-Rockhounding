package al132.atmrockhounding.tile;

import java.util.ArrayList;

import al132.atmrockhounding.ModConfig;
import al132.atmrockhounding.Reference;
import al132.atmrockhounding.client.gui.GuiMetalAlloyer;
import al132.atmrockhounding.items.ModItems;
import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.recipes.machines.MetalAlloyerRecipe;
import al132.atmrockhounding.tile.WrappedItemHandler.WriteMode;
import al132.atmrockhounding.utils.FuelUtils;
import al132.atmrockhounding.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.RangedWrapper;

public class TileMetalAlloyer extends TileMachine {


	public static final int SLOT_INPUTS[] = new int[]{0,1,2,3,4,5,5};
	//public static final int SLOT_FUEL = 6;
	public static final int SLOT_CONSUMABLE = 7;
	public static final int SLOT_INDUCTOR = 8;

	public static final int SLOT_OUTPUT = 0;
	private MetalAlloyerRecipe currentRecipe = null;

	public TileMetalAlloyer() {
		super(9, 1, 6);

		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot < SLOT_INPUTS.length && inputHasRecipe(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && FuelUtils.isItemFuel(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SLOT_CONSUMABLE && ItemStack.areItemsEqualIgnoreDurability(insertingStack, new ItemStack(ModItems.ingotPattern))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SLOT_INDUCTOR && ItemStack.areItemsEqual(insertingStack, Reference.inductor)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		automationInput = new WrappedItemHandler(input,WriteMode.IN_OUT);
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
		return ModRecipes.getImmutableAlloyerRecipes().stream().anyMatch(x -> x.matches(getDustHandler()));
	}


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

	@Override
	public void update(){
		if(!worldObj.isRemote){
			if(input.getStackInSlot(FUEL_SLOT) != null){
				fuelHandler();
			}
			if(canAlloy()){
				execute(); 
			}
			this.markDirtyClient();
		}
	}

	private void execute() {
		if(cookTime == 0) {

		}
		cookTime++;
		powerCount--;
		if(cookTime >= getCookTimeMax()) {
			cookTime = 0; 
			consumeInput();
			addOutput();
		}
	}

	private void consumeInput(){
		for(int i=0;i<getDustHandler().getSlots();i++){
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

	private boolean canOutput(){
		if(this.output.getStackInSlot(SLOT_OUTPUT) == null) return true;
		else if(this.currentRecipe == null) return false;
		else return (currentRecipe.getOutputs().get(0).stackSize + output.getStackInSlot(0).stackSize) <= output.getStackInSlot(0).getMaxStackSize()
				&& ItemHandlerHelper.canItemStacksStack(currentRecipe.getOutputs().get(0),output.getStackInSlot(SLOT_OUTPUT));
	}

	private void addOutput(){
		output.setOrAdd(SLOT_OUTPUT, currentRecipe.getOutputs().get(0));
	}

	private boolean canAlloy() {
		System.out.println("========");
		System.out.println("Matches: " + matchesRecipe());
		System.out.println("hasConsumable: " + hasConsumable());
		System.out.println("power: " + (powerCount >= getCookTimeMax()));
		System.out.println("canOutput: " + canOutput());
		
		return  matchesRecipe()
				&& hasConsumable() 
				&& powerCount >= getCookTimeMax()
				&& canOutput();
	}


	private boolean hasConsumable() {
		return input.getStackInSlot(SLOT_CONSUMABLE) != null;
	}

	@Override
	public int getGUIHeight() {
		return GuiMetalAlloyer.HEIGHT;
	}

	@Override
	public boolean canInduct(){
		return input.getStackInSlot(SLOT_INDUCTOR) != null;
	}
}