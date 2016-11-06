package al132.atmrockhounding.tile;

import java.util.Random;

import al132.atmrockhounding.utils.FuelUtils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.RangedWrapper;

public class TileMachine extends TileInv  implements IRFStorage {

	public int powerMax = 32000;
	public int powerCount = 0;
	public int redstoneCount = 0;
	public int redstoneMax = 5000;
	public int cookTime = 0;
	
	public Random rand = new Random();

	public int FUEL_SLOT;

	public TileMachine(int inputSlots, int outputSlots, int fuelSlot) {
		super(inputSlots, outputSlots);
		this.FUEL_SLOT = fuelSlot;
	}


	protected void fuelHandler() {
		if(input.getStackInSlot(FUEL_SLOT) != null && FuelUtils.isItemFuel(input.getStackInSlot(FUEL_SLOT)) ){
			if( powerCount <= (powerMax - FuelUtils.getItemBurnTime(input.getStackInSlot(FUEL_SLOT))) ){
				burnFuel();
			}
		}
	}

	protected void burnFuel() {
		powerCount += FuelUtils.getItemBurnTime(input.getStackInSlot(FUEL_SLOT));
		ItemStack stack = input.getStackInSlot(FUEL_SLOT);
		stack.stackSize--;
		input.setStackInSlot(FUEL_SLOT, stack);
		if(input.getStackInSlot(FUEL_SLOT).stackSize <= 0){
			input.setStackInSlot(FUEL_SLOT, input.getStackInSlot(FUEL_SLOT).getItem().getContainerItem(input.getStackInSlot(FUEL_SLOT)));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.powerCount = compound.getInteger("PowerCount");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("PowerCount", this.powerCount);
		return compound;
	}

	public IItemHandlerModifiable capOutput(){
		if(input.getStackInSlot(FUEL_SLOT) != null){
			if(input.getStackInSlot(FUEL_SLOT).getItem() == Items.BUCKET){
				return new CombinedInvWrapper(automationOutput,new RangedWrapper(input,1,2));
			}
		}
		return automationOutput;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if(facing == EnumFacing.DOWN){
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(capOutput());
			}
			else {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(automationInput);
			}
		}
		return super.getCapability(capability, facing);
	}

	public void update() {}

	@Override
	public int getPower() {return this.powerCount; }

	@Override
	public int getPowerMax() { return this.powerMax; }

	@Override
	public int getRedstoneMax() { return this.redstoneMax; }

	@Override
	public int getRedstone() {return this.redstoneCount; }

	@Override
	public void setPower(int amount) { this.powerCount = amount; }

	@Override
	public void addPower(int amount) { this.powerCount+= amount;	}

	@Override
	public void setRedstone(int amount) {this.redstoneCount = amount;}

	@Override
	public void addRedstone(int amount) {this.redstoneCount+=amount;}

	@Override
	public boolean canInduct() {return false;}

	@Override
	public int getGUIHeight() {return 0; }
}
