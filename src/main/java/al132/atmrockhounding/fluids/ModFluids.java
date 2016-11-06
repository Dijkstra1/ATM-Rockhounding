package al132.atmrockhounding.fluids;

import al132.atmrockhounding.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {
//check out https://github.com/droidicus/AquaRegia/tree/9e2595c32bce2df9e7e19b39f04383a4c0f40d2f ?
	public static String fluidRootDir = ":textures/fluids/";
	static BaseFluidBlock sulfuricBlock;

	public static void init(){
		Fluid sulfuricAcid = new Fluid("sulfuric_acid",
				new ResourceLocation(Reference.MODID + fluidRootDir + "sulfuric_acid_still.png"),
				new ResourceLocation(Reference.MODID + fluidRootDir + "sulfuric_acid_flow.png"));
		
		FluidRegistry.registerFluid(sulfuricAcid);
		FluidRegistry.addBucketForFluid(sulfuricAcid);
		sulfuricBlock = new BaseFluidBlock("sulfuricAcid", sulfuricAcid);
	}

}
