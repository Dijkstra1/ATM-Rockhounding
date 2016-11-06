package al132.atmrockhounding.fluids;

import al132.atmrockhounding.ATMRockhounding;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BaseFluidBlock extends BlockFluidClassic {

	public BaseFluidBlock(String name, Fluid fluid)  {
		super(fluid, Material.WATER);
		setRegistryName(name);
		setUnlocalizedName(name);
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
		ATMRockhounding.proxy.initFluidModel(this, fluid);
	}

}
