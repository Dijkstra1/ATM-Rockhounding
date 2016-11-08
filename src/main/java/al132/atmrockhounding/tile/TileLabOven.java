package al132.atmrockhounding.tile;

import al132.atmrockhounding.ModConfig;
import al132.atmrockhounding.Reference;
import al132.atmrockhounding.client.gui.GuiLabOven;
import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.recipes.machines.LabOvenRecipe;
import al132.atmrockhounding.tile.WrappedItemHandler.WriteMode;
import al132.atmrockhounding.utils.FuelUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TileLabOven extends TileMachine implements IFluidHandlingTile{

	private int redstoneCharge = this.getCookTimeMax();

	//Input handler slots
	public static final int SOLUTE_SLOT = 0;
	//						 FUEL_SLOT = 1;
	public static final int SOLVENT_SLOT = 2;
	public static final int OUTPUT_SLOT = 3;
	public static final int REDSTONE_SLOT = 4;

	public FluidTank inputTank;
	public FluidTank outputTank;
	LabOvenRecipe currentRecipe;

	public TileLabOven() {
		super(7, 0, 1);

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
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == SOLUTE_SLOT && soluteHasRecipe(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && FuelUtils.isItemFuel(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SOLVENT_SLOT 
						&& solventHasRecipe(FluidUtil.getFluidContained(insertingStack))){
					return super.insertItem(slot, insertingStack, simulate);
				}

				if(slot == REDSTONE_SLOT &&
						(insertingStack.getItem() == Items.REDSTONE || (insertingStack.isItemEqual(Reference.inductor)))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == OUTPUT_SLOT && ItemStack.areItemsEqual(insertingStack, new ItemStack(Items.BUCKET))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};

		automationInput = new WrappedItemHandler(input,WriteMode.IN_OUT);
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
		this.cookTime = compound.getInteger("CookTime");
		this.inputTank.readFromNBT(compound.getCompoundTag("InputTank"));
		this.outputTank.readFromNBT(compound.getCompoundTag("OutputTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("CookTime", this.cookTime);

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
		if(input.getStackInSlot(FUEL_SLOT) != null){fuelHandler();}
		if(input.getStackInSlot(REDSTONE_SLOT) != null){redstoneHandler();}

		if(!worldObj.isRemote){
			
			if( FluidUtil.getFluidContained(input.getStackInSlot(SOLVENT_SLOT)) != null){
				if(FluidUtil.tryEmptyContainer(input.getStackInSlot(SOLVENT_SLOT), inputTank, 1000, null, false) != null){
					input.setStackInSlot(SOLVENT_SLOT,FluidUtil.tryEmptyContainer(input.getStackInSlot(SOLVENT_SLOT), inputTank, 1000, null, true));
				}
			}

			if(canSynthesize()){
				execute();
			}

			if(hasOutputSlotBucket() && outputTank.getFluidAmount() >= 1000){
				if(null != FluidUtil.tryFillContainer(input.getStackInSlot(OUTPUT_SLOT), outputTank, 1000, null, false)){
					input.setStackInSlot(OUTPUT_SLOT,FluidUtil.tryFillContainer(input.getStackInSlot(OUTPUT_SLOT), outputTank, 1000, null, true));
				}
			}
		}
		this.markDirtyClient();
	}

	public boolean canSynthesize(){
		currentRecipe = ModRecipes.labOvenRecipes.stream().filter(recipe -> 
		ItemStack.areItemsEqual(recipe.getSolute(), input.getStackInSlot(SOLUTE_SLOT))
		&& inputTank.getFluid().containsFluid(recipe.getSolvent())).findAny().orElse(null);

		return currentRecipe != null
				&& this.getRedstone() >= this.getCookTimeMax()
				&& this.getPower() >= this.getCookTimeMax();
	}

	private void execute() {
		cookTime++;
		powerCount--;
		redstoneCount --; 
		if(cookTime >= getCookTimeMax()) {
			cookTime = 0; 
			handleOutput();
			currentRecipe = null;
		}
	}


	private void handleOutput() {

		input.decrementSlot(SOLUTE_SLOT);
		inputTank.getFluid().amount -= 500;
		outputTank.fillInternal(currentRecipe.getOutput(), true);
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
				&& input.getStackInSlot(REDSTONE_SLOT).isItemEqual(Reference.inductor);
	}

	@Override
	public int getGUIHeight() {
		return GuiLabOven.HEIGHT;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return true;
		else return super.hasCapability(capability, facing);
	}

	public FluidHandlerConcatenate getCombinedTank(){
		return new FluidHandlerConcatenate(inputTank,outputTank);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return (T) getCombinedTank();
		return super.getCapability(capability, facing);
	}
}