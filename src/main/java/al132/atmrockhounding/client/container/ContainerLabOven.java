package al132.atmrockhounding.client.container;

import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.tile.TileLabOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerLabOven extends ContainerBase<TileLabOven> {

	Slot templateSlot;

	public ContainerLabOven(InventoryPlayer playerInventory, TileLabOven tile){
		super(playerInventory,tile);

	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler template = tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 44, 59));//input solute
		this.addSlotToContainer(new SlotItemHandler(input, 1, 8, 20));//fuel
		this.addSlotToContainer(new SlotItemHandler(input, 2, 116, 59));//input solvent
		this.addSlotToContainer(new SlotItemHandler(input, 3, 80, 59));//output
		this.addSlotToContainer(new SlotItemHandler(input, 4, 152, 20));//input redstone
		this.addSlotToContainer(new SlotItemHandler(input, 5, 28, 20));//Previous recipe
		this.addSlotToContainer(new SlotItemHandler(input, 6, 132, 20));//Next recipe

		templateSlot = this.addSlotToContainer(new SlotItemHandler(template, 0, 44, 42));//solute template
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 7){
			return null;
		}else if(slot == 5 && this.tile.recipeDisplayIndex >= 0){
			this.tile.recipeDisplayIndex--;
			this.tile.resetGrid();
			this.tile.recipeScan = true;
			return null;
		}else if(slot == 6 && this.tile.recipeDisplayIndex < ModRecipes.labOvenRecipes.size()-1){
			this.tile.recipeDisplayIndex++;
			this.tile.resetGrid();
			this.tile.recipeScan = true;
			return null;
		}else{
			return super.slotClick(slot, dragType, clickTypeIn, player);
		}
	}
	
	//Prevent from merging into stack 7, the stack with the recipe template
	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection)
    {
		if(super.mergeItemStack(stack, startIndex, 7, reverseDirection)){
			return true;
		}
		else {
			return super.mergeItemStack(stack, 8, endIndex, reverseDirection);
		}
    }
}