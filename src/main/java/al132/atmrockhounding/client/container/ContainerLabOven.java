package al132.atmrockhounding.client.container;

import al132.atmrockhounding.tile.TileLabOven;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
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

		this.addSlotToContainer(new SlotItemHandler(input, 0, 44, 59));//input solute
		this.addSlotToContainer(new SlotItemHandler(input, 1, 8, 20));//fuel
		this.addSlotToContainer(new SlotItemHandler(input, 2, 116, 59));//input solvent
		this.addSlotToContainer(new SlotItemHandler(input, 3, 80, 59));//output
		this.addSlotToContainer(new SlotItemHandler(input, 4, 152, 20));//input redstone
		this.addSlotToContainer(new SlotItemHandler(input, 5, 28, 20));//Previous recipe
		this.addSlotToContainer(new SlotItemHandler(input, 6, 132, 20));//Next recipe

	}
}