package al132.atmrockhounding.client.container;

import al132.atmrockhounding.tile.TileChemicalExtractor;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerChemicalExtractor extends ContainerBase<TileChemicalExtractor>{

    public ContainerChemicalExtractor(InventoryPlayer playerInventory, TileChemicalExtractor tile){
    	super(playerInventory,tile);
    }
    
	@Override
	protected void addOwnSlots() {

		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();
		
        this.addSlotToContainer(new SlotItemHandler(input, 0, 57, 52));//input
        this.addSlotToContainer(new SlotItemHandler(input, 1, 8, 8));//fuel
        this.addSlotToContainer(new SlotItemHandler(input, 2, 28, 8));//redstone
        this.addSlotToContainer(new SlotItemHandler(input, 3, 57, 74));//consumable
        this.addSlotToContainer(new SlotItemHandler(input, 4, 8, 90));//syngas
        this.addSlotToContainer(new SlotItemHandler(input, 5, 28, 90));//fluo

        //cabinets
		for(int x=0; x<=6; x++){
			for(int y=0; y<=7; y++){
		        this.addSlotToContainer(new SlotItemHandler(output, (x*8)+y, 88 + (19 * y) , 22 + (21* x)));//output
			}
		}
	}
}