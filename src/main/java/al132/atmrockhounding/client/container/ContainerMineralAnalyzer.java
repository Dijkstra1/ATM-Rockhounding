package al132.atmrockhounding.client.container;

import al132.atmrockhounding.tile.TileMineralAnalyzer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMineralAnalyzer extends ContainerBase<TileMineralAnalyzer>{

	public ContainerMineralAnalyzer(InventoryPlayer playerInventory, TileMineralAnalyzer tile){
		super(playerInventory, tile);
	}
	
	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 34, 24));//input
		this.addSlotToContainer(new SlotItemHandler(input, 1, 6, 34));//fuel
		this.addSlotToContainer(new SlotItemHandler(input, 2, 70, 46));//consumable
		//this.addSlotToContainer(new SlotItemHandler(input, 3, 144, 24));//sulf
		//this.addSlotToContainer(new SlotItemHandler(input, 4, 144, 46));//chlo
		//this.addSlotToContainer(new SlotItemHandler(input, 5, 144, 68));//fluo
		this.addSlotToContainer(new SlotItemHandler(input, 6, 6, 14));//inductor
		
		this.addSlotToContainer(new SlotItemHandler(output, 0, 70, 72));//output
	}
}