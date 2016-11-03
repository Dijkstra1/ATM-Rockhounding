package al132.atmrockhounding.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMetaBase extends ItemBase {
	
	protected String[] itemArray;

	public ItemMetaBase(String name, String[] array) {
		super(name);
		setHasSubtypes(true);
		this.itemArray = array;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		if( i < 0 || i >= itemArray.length){ i = 0; }
		return super.getUnlocalizedName() + "." + itemArray[i];
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for(int i = 0; i < itemArray.length; i++){subItems.add(new ItemStack(itemIn, 1, i));}
	}
}