package al132.atmrockhounding;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class Reference {
	public static final String MODID = "atmrockhounding";
	public static final String MODNAME = "ATM Rockhounding";
	public static final String VERSION = "v1.1.15";
	public static final String CLIENT_PROXY_CLASS = "al132.atmrockhounding.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "al132.atmrockhounding.CommonProxy";

	public static String pathPrefix = "atmrockhounding:";


	public static CreativeTabs RockhoundingChemistry = new CreativeTabs("atmRockhounding") {
		public Item getTabIconItem() {
			return Items.IRON_INGOT; 
		}
	};
}