package al132.atmrockhounding.tile;

import java.util.ArrayList;

import al132.atmrockhounding.ModConfig;
import al132.atmrockhounding.Reference;
import al132.atmrockhounding.client.gui.GuiMetalAlloyer;
import al132.atmrockhounding.items.ModItems;
import al132.atmrockhounding.recipes.MetalAlloyerRecipe;
import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.tile.WrappedItemHandler.WriteMode;
import al132.atmrockhounding.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.wrapper.RangedWrapper;

public class TileMetalAlloyer extends TileMachine {


	public static final int SLOT_INPUTS[] = new int[]{0,1,2,3,4,5,5};
	//public static final int SLOT_FUEL = 6;
	public static final int SLOT_CONSUMABLE = 7;
	public static final int SLOT_INDUCTOR = 8;

	public static final int SLOT_OUTPUT = 0;
	public static final int SLOT_SCRAP = 1;
	private MetalAlloyerRecipe currentRecipe = null;
	
	public TileMetalAlloyer() {
		super(9, 2, 6);

		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot < SLOT_INPUTS.length && hasRecipe(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && Utils.isItemFuel(insertingStack)){
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


	public boolean hasRecipe(ItemStack inStack){
		ArrayList<ItemStack> stacksToCheck = new ArrayList<ItemStack>();
		stacksToCheck.addAll(Utils.handlerToStackList(getDustHandler()));
		stacksToCheck.add(inStack);

		return ModRecipes.alloyerRecipes.stream()
				.anyMatch(x ->Utils.listContainsStackList(stacksToCheck, x.getInputs(),false));
	}

	public boolean matchesRecipe(){
		this.currentRecipe = ModRecipes.alloyerRecipes.stream()
				.filter(x -> Utils.listContainsStackList(x.getInputs(), Utils.handlerToStackList(getDustHandler()),true)).findFirst().orElse(null);
		return this.currentRecipe != null;
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
		if(input.getStackInSlot(FUEL_SLOT) != null){
			fuelHandler();
		}
		if(canAlloy()){
			
			execute(); 
		}
		this.markDirtyClient();
	}

	private void execute() {
		if(cookTime == 0) {
			consumeInput();
		}
		cookTime++;
		powerCount--;
		if(cookTime >= getCookTimeMax()) {
			cookTime = 0; 
			addOutput();
		}
	}

	private void consumeInput(){
		for(int i=0;i<getDustHandler().getSlots();i++){
			for(ItemStack recipeStack: currentRecipe.getInputs()){
				if(ItemStack.areItemsEqual(getDustHandler().getStackInSlot(i), recipeStack)){
					for(int j=0; j<recipeStack.stackSize;j++){
						Utils.decrementSlot(getDustHandler(), i);
					}
				}
			}
		}
	}
	
	private boolean canOutput(){
		if(this.output.getStackInSlot(SLOT_OUTPUT) == null) return true;
		else return ItemStack.areItemsEqual(currentRecipe.getOutputs().get(0), output.getStackInSlot(SLOT_OUTPUT));
	}
	
	private void addOutput(){
		output.setOrAdd(SLOT_OUTPUT, currentRecipe.getOutputs().get(0));
	}

	private boolean canAlloy() {
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
		return !(input.getStackInSlot(SLOT_INDUCTOR) == null);
	}
}