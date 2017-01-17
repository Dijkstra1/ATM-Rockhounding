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

		this.addSlotToContainer(new SlotItemHandler(input, 0, 46, 70));//input solute
		this.addSlotToContainer(new SlotItemHandler(input, 1, 21, 5));//fuel
		this.addSlotToContainer(new SlotItemHandler(input, 2, 148, 23));//input solvent
		this.addSlotToContainer(new SlotItemHandler(input, 3, 118, 103));//output

	}
}