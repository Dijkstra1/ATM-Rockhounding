package al132.atmrockhounding.tile;

import al132.atmrockhounding.ModConfig;
import al132.atmrockhounding.Reference;
import al132.atmrockhounding.blocks.ModBlocks;
import al132.atmrockhounding.client.gui.GuiMineralAnalyzer;
import al132.atmrockhounding.enums.EnumFluid;
import al132.atmrockhounding.items.ModItems;
import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.utils.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileMineralAnalyzer extends TileMachine {

	private int consumedSulf = 1;
	private int consumedChlo = 3;
	private int consumedFluo = 2;

	private static final int INPUT_SLOT = 0;
	//						 FUEL_SLOT = 1;
	private static final int CONSUMABLE_SLOT = 2;
	private static final int SULFUR_SLOT = 3;
	private static final int CHLOR_SLOT = 4;
	private static final int FLUO_SLOT = 5;
	private static final int INDUCTOR_SLOT = 6;

	private static final int OUTPUT_SLOT = 0;

	public TileMineralAnalyzer() {
		super(7,1,1);

		input =  new MachineStackHandler(7,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				String fluidName = "";
				if(insertingStack.hasTagCompound()) fluidName = insertingStack.getTagCompound().getString("Fluid");

				if(slot == INPUT_SLOT && insertingStack.getMetadata() != 0 && Utils.areItemsEqualIgnoreMeta(new ItemStack(ModBlocks.mineralOres), insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && Utils.isItemFuel(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CONSUMABLE_SLOT && insertingStack.getItem() == ModItems.testTube){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SULFUR_SLOT && fluidName.equals("Sulfuric Acid") && ItemStack.areItemsEqual(insertingStack, new ItemStack(ModItems.chemicalItems,1,0))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CHLOR_SLOT && fluidName.equals("Hydrochloric Acid") && ItemStack.areItemsEqual(insertingStack, new ItemStack(ModItems.chemicalItems,1,0))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FLUO_SLOT && fluidName.equals("Hydrofluoric Acid") && ItemStack.areItemsEqual(insertingStack, new ItemStack(ModItems.chemicalItems,1,0))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == INDUCTOR_SLOT && ItemStack.areItemsEqual(insertingStack, Reference.inductor)){
					return super.insertItem(slot, insertingStack, simulate);
				}

				return insertingStack;
			}
		};

		this.automationInput = new WrappedItemHandler(input, WrappedItemHandler.WriteMode.IN_OUT);
	}

	private boolean isOutputEmpty(){
		return output.getStackInSlot(OUTPUT_SLOT) == null;
	}

	private boolean hasConsumable(){
		return !(input.getStackInSlot(CONSUMABLE_SLOT) == null);
	}

	private boolean hasInput(){
		return !(input.getStackInSlot(INPUT_SLOT) == null);
	}

	//----------------------- CUSTOM -----------------------

	public int getCookTimeMax(){
		return ModConfig.speedAnalyzer;
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
			if(canAnalyze()){
				cookTime++; powerCount--;
				if(cookTime >= getCookTimeMax()) {
					cookTime = 0; 
					handleSlots();
				}
			}
			this.markDirtyClient();
		}
	}

	private void handleSlots() {
		int quantity = 0;

		calculateOutput(input.getStackInSlot(INPUT_SLOT).getItemDamage());
		input.damageSlot(CONSUMABLE_SLOT);
		input.decrementSlot(INPUT_SLOT);

		//decrease acids
		quantity = input.getStackInSlot(SULFUR_SLOT).getTagCompound().getInteger("Quantity") - consumedSulf;
		input.getStackInSlot(SULFUR_SLOT).getTagCompound().setInteger("Quantity", quantity);
		if(quantity <= 0){input.getStackInSlot(SULFUR_SLOT).getTagCompound().setString("Fluid", EnumFluid.EMPTY.getName());}
		quantity = input.getStackInSlot(CHLOR_SLOT).getTagCompound().getInteger("Quantity") - consumedChlo;
		input.getStackInSlot(CHLOR_SLOT).getTagCompound().setInteger("Quantity", quantity);
		if(quantity <= 0){input.getStackInSlot(CHLOR_SLOT).getTagCompound().setString("Fluid", EnumFluid.EMPTY.getName());}
		quantity = input.getStackInSlot(FLUO_SLOT).getTagCompound().getInteger("Quantity") - consumedFluo;
		input.getStackInSlot(FLUO_SLOT).getTagCompound().setInteger("Quantity", quantity);
		if(quantity <= 0){input.getStackInSlot(FLUO_SLOT).getTagCompound().setString("Fluid", EnumFluid.EMPTY.getName());}
	}


	private void calculateOutput(int entryMineral) {

		Item outItem = ModItems.arsenateShards;
		switch(entryMineral){
		case 1: outItem = ModItems.arsenateShards; break;
		case 2: outItem = ModItems.borateShards; break;
		case 3: outItem = ModItems.carbonateShards; break;
		case 4: outItem = ModItems.halideShards; break;
		case 5: outItem = ModItems.nativeShards; break;
		case 6: outItem = ModItems.oxideShards; break;
		case 7: outItem = ModItems.phosphateShards; break;
		case 8: outItem = ModItems.silicateShards; break;
		case 9: outItem = ModItems.sulfateShards; break;
		case 10: outItem = ModItems.sulfideShards; break;
		default: break;
		}
		handleOutput(outItem, 
				Utils.calculateProbability(ModRecipes.analyzerRecipes.get(entryMineral-1).getProbabilityStack()).getItemDamage());
		//handleOutput(outItem,ModRecipes.analyzerRecipes.get(entryMineral-1).getOutputs().calculateOutput().getItemDamage());
	}

	private void handleOutput(Item mineral, int meta) {
		ItemStack outputStack = new ItemStack(mineral, rand.nextInt(ModConfig.maxMineral) + 1, meta);
		if(isOutputEmpty()){
			output.setStackInSlot(OUTPUT_SLOT, outputStack.copy());
		}else if(output.getStackInSlot(OUTPUT_SLOT).isItemEqual(outputStack) &&
				output.getStackInSlot(OUTPUT_SLOT).stackSize < output.getStackInSlot(OUTPUT_SLOT).getMaxStackSize()){
			//slots[outputSlot].stackSize += outPutStack.stackSize;
			//if(slots[outputSlot].stackSize > slots[outputSlot].getMaxStackSize()){slots[outputSlot].stackSize = output.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();}
		}
	}

	private boolean canAnalyze() {
		return  (output.getStackInSlot(OUTPUT_SLOT) == null
				&& hasInput()
				&& hasConsumable()
				&& powerCount >= getCookTimeMax()
				&& hasAcid(SULFUR_SLOT, EnumFluid.SULFURIC_ACID, consumedSulf)
				&& hasAcid(CHLOR_SLOT, EnumFluid.HYDROCHLORIC_ACID, consumedChlo)
				&& hasAcid(FLUO_SLOT, EnumFluid.HYDROFLUORIC_ACID, consumedFluo));
	}

	private boolean hasTank(int acidSlot) {
		return input.getStackInSlot(acidSlot) != null && input.getStackInSlot(acidSlot).getItem() == ModItems.chemicalItems && input.getStackInSlot(acidSlot).getItemDamage() == 0;
	}
	private boolean hasAcid(int acidSlot, EnumFluid acidType, int acidConsumed) {
		if(hasTank(acidSlot)){
			if(input.getStackInSlot(acidSlot).hasTagCompound()){
				if(input.getStackInSlot(acidSlot).getTagCompound().getString("Fluid").matches(acidType.getName())){
					if(input.getStackInSlot(acidSlot).getTagCompound().getInteger("Quantity") >= acidConsumed){
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean canInduct(){
		return !(input.getStackInSlot(INDUCTOR_SLOT) == null);
	}

	@Override
	public int getGUIHeight() {
		return GuiMineralAnalyzer.HEIGHT;
	}
}