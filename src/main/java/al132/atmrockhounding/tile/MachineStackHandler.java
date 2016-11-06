package al132.atmrockhounding.tile;

import al132.atmrockhounding.enums.EnumFluid;
import al132.atmrockhounding.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class MachineStackHandler extends ItemStackHandler{

	TileInv tile;
	public MachineStackHandler(int slots,TileInv tile) {
		super(slots);
		this.tile = tile;

	}

	public void incrementSlot(int slot){
		ItemStack temp = this.getStackInSlot(slot);
		if(temp.stackSize+1 <= temp.getMaxStackSize()){
			temp.stackSize++;
		}
		this.setStackInSlot(slot, temp);
	}

	public void setOrIncrement(int slot, ItemStack stackToSet){
		ItemStack temp = stackToSet.copy();
		if(this.getStackInSlot(slot) == null){
			this.setStackInSlot(slot, temp);
		}
		else{
			incrementSlot(slot);
		}
	}

	//this and setOrIncrement can be consolidated.. just don't feel like picking up the pieces
	//at the time of writing
	public void setOrAdd(int slot, ItemStack stackToSet){
		ItemStack temp = stackToSet.copy();
		if(this.getStackInSlot(slot) == null){
			this.setStackInSlot(slot, temp);
		}
		else{
			for(int i=0; i < temp.stackSize; i++){
				incrementSlot(slot);
			}
		}
	}

	public void decrementSlot(int slot){
		ItemStack temp = this.getStackInSlot(slot);
		temp.stackSize--;
		if(temp.stackSize <= 0) this.setStackInSlot(slot, null);
		else this.setStackInSlot(slot, temp);
	}

	public void decrementFluid(int slot){
		ItemStack stack = this.getStackInSlot(slot);
		int newQuantity = stack.getTagCompound().getInteger("Quantity")-1;
		if(stack.hasTagCompound()){
			if(!(stack.getTagCompound().getString("Fluid").equals(EnumFluid.EMPTY.getName()))){
				stack.getTagCompound().setInteger("Quantity", newQuantity);
				if(newQuantity <= 0){
					stack.getTagCompound().setString("Fluid", EnumFluid.EMPTY.getName());
					stack.getTagCompound().setInteger("Quantity", 0);
				}
			}
		}
	}

	public void decrementFluid(int slot, int quantity){
		for(int i=0;i<quantity;i++){
			decrementFluid(slot);
		}
	}

	public void damageSlot(int slot){
		this.setStackInSlot(slot, Utils.damage(this.getStackInSlot(slot)));
	}

	//Copied from ItemStackHandler.insertItem(..
	public ItemStack internalInsertion(int slot, ItemStack stack, boolean simulate){
		{
			if (stack == null || stack.stackSize == 0)
				return null;

			validateSlotIndex(slot);

			ItemStack existing = this.stacks[slot];

			int limit = getStackLimit(slot, stack);

			if (existing != null)
			{
				if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
					return stack;

				limit -= existing.stackSize;
			}

			if (limit <= 0)
				return stack;

			boolean reachedLimit = stack.stackSize > limit;

			if (!simulate)
			{
				if (existing == null)
				{
					this.stacks[slot] = reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack;
				}
				else
				{
					existing.stackSize += reachedLimit ? limit : stack.stackSize;
				}
				onContentsChanged(slot);
			}

			return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.stackSize - limit) : null;
		}
	}
}