package al132.atmrockhounding.tile;

import al132.atmrockhounding.ModConfig;
import al132.atmrockhounding.client.gui.GuiLabOven;
import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.recipes.machines.LabOvenRecipe;
import al132.atmrockhounding.tile.WrappedItemHandler.WriteMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TileLabOven extends TileMachine implements IFluidHandlingTile{

	//Input handler slots

	public static final int SOLVENT_SLOT = 2;
	public static final int OUTPUT_SLOT = 3;

	public static final int ENERGY_PER_TICK = 10;
	
	public FluidTank inputTank;
	public FluidTank outputTank;

	LabOvenRecipe currentRecipe;

	@Override
	public int getFuelIndex(){return 1;}
	
	@Override
	public int getInputIndex(){return 0;}
	
	@Override
	public int getConsumableIndex(){return -1;}
	
	public TileLabOven() {
		super(4, 0, 1);

		inputTank = new FluidTank(Fluid.BUCKET_VOLUME * 10){
			@Override
			public boolean canFillFluidType(FluidStack fluid)
			{
				return solventHasRecipe(fluid);
			}
		};
		
		inputTank.setTileEntity(this);
		inputTank.setCanFill(true);
		inputTank.setCanDrain(false);

		outputTank = new FluidTank(Fluid.BUCKET_VOLUME * 10);
		outputTank.setTileEntity(this);
		outputTank.setCanDrain(true);
		outputTank.setCanFill(false);

		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
		    protected int getStackLimit(int slot, ItemStack stack) {
				if(slot == OUTPUT_SLOT) return 1;
				else return stack.getMaxStackSize();
		    }
				
			
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == getInputIndex() && soluteHasRecipe(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == getFuelIndex() && isInductorOrFuel(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SOLVENT_SLOT 
						&& solventHasRecipe(FluidUtil.getFluidContained(insertingStack))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == OUTPUT_SLOT && ItemStack.areItemsEqual(insertingStack, new ItemStack(Items.BUCKET))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};

		automationInput = new WrappedItemHandler(input,WriteMode.IN_OUT){
			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate){
				return null;
			}
		};

		this.markDirtyClient();
	}
	


	public int getCookTimeMax(){
		return ModConfig.speedLabOven;
	}

	public boolean interactWithBucket(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {

		boolean didFill = FluidUtil.interactWithFluidHandler(heldItem, this.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side), player);
		this.markDirtyClient();
		return didFill;
	}

	public static boolean soluteHasRecipe(ItemStack insertingStack){
		return ModRecipes.labOvenRecipes.stream().anyMatch(
				recipe -> ItemStack.areItemsEqual(recipe.getSolute(), insertingStack));
	}

	public static boolean solventHasRecipe(FluidStack insertingStack){
		return ModRecipes.labOvenRecipes.stream().anyMatch(
				recipe -> recipe.getSolvent().equals(insertingStack));
	}



	public boolean hasOutputSlotBucket(){
		return ItemStack.areItemsEqual(new ItemStack(Items.BUCKET), input.getStackInSlot(OUTPUT_SLOT));
	}


	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.inputTank.readFromNBT(compound.getCompoundTag("InputTank"));
		this.outputTank.readFromNBT(compound.getCompoundTag("OutputTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);

		NBTTagCompound inputTankNBT = new NBTTagCompound();
		this.inputTank.writeToNBT(inputTankNBT);
		compound.setTag("InputTank", inputTankNBT);

		NBTTagCompound outputTankNBT = new NBTTagCompound();
		this.outputTank.writeToNBT(outputTankNBT);
		compound.setTag("OutputTank", outputTankNBT);

		return compound;
	}


	//----------------------- PROCESS -----------------------

	@Override
	public void update(){
		
		if(input.getStackInSlot(getFuelIndex()) != null){fuelHandler();}

		if(!worldObj.isRemote){

			if( FluidUtil.getFluidContained(input.getStackInSlot(SOLVENT_SLOT)) != null){
				if(FluidUtil.tryEmptyContainer(input.getStackInSlot(SOLVENT_SLOT), inputTank, 1000, null, false) != null){
					input.setStackInSlot(SOLVENT_SLOT,FluidUtil.tryEmptyContainer(input.getStackInSlot(SOLVENT_SLOT), inputTank, 1000, null, true));
				}
			}

			if(canProcess()){
				execute();
			}

			if(hasOutputSlotBucket() && outputTank.getFluidAmount() >= 1000){
				if(null != FluidUtil.tryFillContainer(input.getStackInSlot(OUTPUT_SLOT), outputTank, 1000, null, false)){
					input.setStackInSlot(OUTPUT_SLOT,FluidUtil.tryFillContainer(input.getStackInSlot(OUTPUT_SLOT), outputTank, 1000, null, true));
				}
			}
			
			if(this.inputTank.getFluidAmount() < 1) inputTank.setFluid(null);
			if(this.outputTank.getFluidAmount() < 1) outputTank.setFluid(null);
			this.markDirtyClient();
		}
	}

	@Override
	public boolean canProcess(){
		if(inputTank.getFluid() != null){
			currentRecipe = ModRecipes.labOvenRecipes.stream().filter(recipe -> 
			ItemStack.areItemsEqual(recipe.getSolute(), input.getStackInSlot(getInputIndex()))
			&& inputTank.getFluid().containsFluid(recipe.getSolvent())).findAny().orElse(null);
		}
		Boolean outputIsValidTarget = false;
		if(outputTank.getFluid() == null) outputIsValidTarget = true;
		else if(currentRecipe != null){
				outputIsValidTarget = (currentRecipe.getOutput().getFluid() == outputTank.getFluid().getFluid());
		}
		
		return currentRecipe != null
				&& outputIsValidTarget
				&& (this.getEnergyStorage().getEnergyStored() + cookTime) >= this.getCookTimeMax()
				&& (currentRecipe.getOutput().amount + outputTank.getFluidAmount()) <= outputTank.getCapacity();
	}

	private void execute() {
		cookTime++;
		energyStorage.extractEnergy(ENERGY_PER_TICK, false);
		if(cookTime >= getCookTimeMax()) {
			cookTime = 0; 
			handleOutput();
			currentRecipe = null;
		}
	}


	private void handleOutput() {

		input.decrementSlot(getInputIndex());
		inputTank.getFluid().amount -= 500;
		outputTank.fillInternal(currentRecipe.getOutput(), true);
	}

	@Override
	public int getGUIHeight() {
		return GuiLabOven.HEIGHT;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank(){
		return new FluidHandlerConcatenate(inputTank,outputTank);
	}
}