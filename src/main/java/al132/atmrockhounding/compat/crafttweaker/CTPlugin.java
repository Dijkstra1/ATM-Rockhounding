package al132.atmrockhounding.compat.crafttweaker;

import minetweaker.MineTweakerAPI;

public class CTPlugin {

	public CTPlugin(){
		MineTweakerAPI.registerClass(CTMineralSizer.class);
		MineTweakerAPI.registerClass(CTMetalAlloyer.class);
	}
}
