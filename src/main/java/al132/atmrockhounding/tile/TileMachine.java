package al132.atmrockhounding.tile;

import java.util.Random;

import al132.atmrockhounding.items.ModItems;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.RangedWrapper;

public abstract class TileMachine extends TileInv implements IEnergyReceiver {

	public int cookTime = 0;
	public static final int ENERGY_PER_DUST = 500;

	public Random rand = new Random();

	protected EnergyStorage energyStorage;

	public TileMachine(int inputSlots, int outputSlots, int fuelSlot) {
		super(inputSlots, outputSlots);
		energyStorage = new EnergyStorage(30000);

	}

	public abstract boolean canProcess();
	public abstract int getInputIndex();
	public abstract int getFuelIndex();
	public abstract int getConsumableIndex();

	public static boolean isInductorOrFuel(ItemStack stack){
		if (stack == null) return false;
		
		return ItemStack.areItemsEqual(stack, new ItemStack(ModItems.energized_fuel_blend))
			||  ItemStack.areItemsEqual(stack, new ItemStack(ModItems.inductionHeatingElement));
	}
	
	public IEnergyStorage getEnergyStorage(){
		return this.energyStorage;
	}

	public boolean hasConsumable(){
		return input.getStackInSlot(getConsumableIndex()) != null;
	}

	public boolean hasInput(){
		return input.getStackInSlot(getInputIndex()) != null;
	}

	protected void fuelHandler() {
		if(ItemStack.areItemsEqual(input.getStackInSlot(getFuelIndex()), new ItemStack(ModItems.energized_fuel_blend))){
			if( energyStorage.getEnergyStored() <= (energyStorage.getMaxEnergyStored() - TileMachine.ENERGY_PER_DUST)){
				burnFuel();
			}
		}
	}

	protected void burnFuel() {
		energyStorage.receiveEnergy(ENERGY_PER_DUST, false);
		ItemStack stack = input.getStackInSlot(getFuelIndex());
		stack.stackSize--;
		input.setStackInSlot(getFuelIndex(), stack);
		if(input.getStackInSlot(getFuelIndex()).stackSize <= 0){
			input.setStackInSlot(getFuelIndex(), input.getStackInSlot(getFuelIndex()).getItem().getContainerItem(input.getStackInSlot(getFuelIndex())));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		energyStorage = new EnergyStorage(compound.getInteger("PowerMax"));
		energyStorage.receiveEnergy(compound.getInteger("PowerCount"), false);
		this.cookTime = compound.getInteger("CookTime");
	}


	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("PowerMax", energyStorage.getMaxEnergyStored());
		compound.setInteger("PowerCount", energyStorage.getEnergyStored());
		compound.setInteger("CookTime", this.cookTime);
		return compound;
	}

	public IItemHandlerModifiable capOutput(){
		if(input.getStackInSlot(getFuelIndex()) != null){
			if(input.getStackInSlot(getFuelIndex()).getItem() == Items.BUCKET){
				return new CombinedInvWrapper(automationOutput,new RangedWrapper(input,1,2));
			}
		}
		return automationOutput;
	}

	public IItemHandler getCombinedAutomationHandlers(){
		return new CombinedInvWrapper(automationInput, capOutput());
	}

	public FluidHandlerConcatenate getCombinedTank(){return null;}

	public boolean canInduct() {
		return ItemStack.areItemsEqual(input.getStackInSlot(getFuelIndex()), new ItemStack(ModItems.inductionHeatingElement));
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		return energyStorage.getEnergyStored();
	}


	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return energyStorage.getMaxEnergyStored();
	}


	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}


	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		if(!canInduct()) return 0;
		else return energyStorage.receiveEnergy(maxReceive, simulate);
	}


	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (this instanceof IFluidHandlingTile 
				&& capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return true;
		else if (capability == CapabilityEnergy.ENERGY) return true;
		else return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getCombinedAutomationHandlers());
		}
		else if (this instanceof IFluidHandlingTile && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(getCombinedTank());
		else if (capability == CapabilityEnergy.ENERGY) {
			if(canInduct()){
				return CapabilityEnergy.ENERGY.cast(energyStorage);
			}
		}
		return super.getCapability(capability, facing);
	}
}
