package al132.atmrockhounding;


import al132.atmrockhounding.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Reference {
	public static final String MODID = "atmrockhounding";
	public static final String MODNAME = "ATM Rockhounding";
	public static final String VERSION = "v1.1.0";
	public static final String CLIENT_PROXY_CLASS = "al132.atmrockhounding.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "al132.atmrockhounding.CommonProxy";

	public static ItemStack inductor = new ItemStack(ModItems.inductionHeatingElement);

	public static String pathPrefix = "atmrockhounding:";


	public static CreativeTabs RockhoundingChemistry = new CreativeTabs("atmRockhounding") {
		public Item getTabIconItem() {
			return Items.IRON_INGOT; 
		}
	};
}
