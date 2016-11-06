package al132.atmrockhounding.client.container;

import al132.atmrockhounding.tile.TileMetalAlloyer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMetalAlloyer extends ContainerBase<TileMetalAlloyer>{

	public ContainerMetalAlloyer(InventoryPlayer playerInventory, TileMetalAlloyer tile){
		super(playerInventory,tile);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();


        for (int x = 0; x < 6; x++){
        	this.addSlotToContainer(new SlotItemHandler(input, x, 53 + (x*18), 51));//input dusts
        }
		this.addSlotToContainer(new SlotItemHandler(input, 6, 8, 20));//fuel
        this.addSlotToContainer(new SlotItemHandler(input, 7, 98, 88));//consumable
        this.addSlotToContainer(new SlotItemHandler(input, 8, 23, 74));//inductor

        this.addSlotToContainer(new SlotItemHandler(output, 0, 76, 88));//output
	}
}