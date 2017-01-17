	package al132.atmrockhounding.client.container;

import al132.atmrockhounding.tile.TileMineralSizer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMineralSizer extends ContainerBase<TileMineralSizer>{

	public ContainerMineralSizer(IInventory playerInventory, TileMineralSizer te) {
		super(playerInventory,te);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 44, 28));//input
		this.addSlotToContainer(new SlotItemHandler(input, 1, 8, 20));//fuel
		this.addSlotToContainer(new SlotItemHandler(input, 2, 81, 48));//consumable

		this.addSlotToContainer(new SlotItemHandler(output, 0, 120, 48));//output
		this.addSlotToContainer(new SlotItemHandler(output, 1, 144, 48));//secondary
		this.addSlotToContainer(new SlotItemHandler(output, 2, 120, 72));//waste
	}
}