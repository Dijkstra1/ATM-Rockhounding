package al132.atmrockhounding.tile;

import al132.atmrockhounding.ModConfig;
import al132.atmrockhounding.Reference;
import al132.atmrockhounding.client.gui.GuiLabOven;
import al132.atmrockhounding.enums.EnumFluid;
import al132.atmrockhounding.items.ModItems;
import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.recipes.machines.LabOvenRecipe;
import al132.atmrockhounding.tile.WrappedItemHandler.WriteMode;
import al132.atmrockhounding.utils.FuelUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileLabOven extends TileMachine {

	private int redstoneCharge = this.getCookTimeMax();

	//Input handler slots
	public static final int SOLUTE_SLOT = 0;
	//						 FUEL_SLOT = 1;
	public static final int SOLVENT_SLOT = 2;
	public static final int OUTPUT_SLOT = 3;
	public static final int REDSTONE_SLOT = 4;

	protected FluidTank inputTank;
	protected FluidTank outputTank;

	public TileLabOven() {
		super(7, 0, 1);


		
		inputTank = new FluidTank(Fluid.BUCKET_VOLUME);
		inputTank.setTileEntity(this);
		inputTank.setCanFill(true);
		
		outputTank = new FluidTank(Fluid.BUCKET_VOLUME);
		outputTank.setTileEntity(this);

		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == SOLUTE_SLOT && isCorrectSolute(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && FuelUtils.isItemFuel(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SOLVENT_SLOT && isCorrectSolvent(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == REDSTONE_SLOT &&
						(insertingStack.getItem() == Items.REDSTONE || (insertingStack.isItemEqual(Reference.inductor)))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				/*if(slot == OUTPUT_SLOT && ItemStack.areItemsEqual(insertingStack, new ItemStack(ModItems.chemicalItems,1,0)) && isValidTank(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}*/
				return insertingStack;
			}
		};

		automationInput = new WrappedItemHandler(input,WriteMode.IN_OUT);
	}

	public int getCookTimeMax(){
		return ModConfig.speedLabOven;
	}
	
	public FluidTank getInputTank(){
		return this.inputTank;
	}


	public boolean interactWithBucket(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {

		boolean didFill = FluidUtil.interactWithFluidHandler(heldItem, this.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side), player);
		this.markDirty();
		world.notifyBlockUpdate(pos, state, world.getBlockState(pos), 8);
		return didFill;
	}

	private boolean isCorrectSolute(ItemStack stack) {
		return false;
	}

	public boolean isCorrectSolvent(ItemStack stack){
		return false;
	}

	public boolean isValidTank(ItemStack stack){
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
		this.cookTime = compound.getInteger("CookTime");
		this.inputTank.readFromNBT(compound);
		this.outputTank.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("CookTime", this.cookTime);
		this.inputTank.writeToNBT(compound);
		this.outputTank.writeToNBT(compound);
		return compound;
	}


	//----------------------- PROCESS -----------------------

	@Override
	public void update(){

		if(input.getStackInSlot(FUEL_SLOT) != null){fuelHandler();}
		if(input.getStackInSlot(REDSTONE_SLOT) != null){redstoneHandler();}
		if(!worldObj.isRemote){
			if(canSynthesize()){
				execute();
			}
			this.markDirtyClient();
		}
	}

	public boolean canSynthesize(){
		return false;
	}

	private void execute() {
		cookTime++;
		powerCount--;
		redstoneCount --; 
		if(cookTime >= getCookTimeMax()) {
			cookTime = 0; 
			handleOutput(getFluidOutput());
		}
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

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return (T) inputTank;
		return super.getCapability(capability, facing);
	}
}