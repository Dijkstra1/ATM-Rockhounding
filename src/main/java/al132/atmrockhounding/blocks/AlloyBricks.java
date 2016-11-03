package al132.atmrockhounding.blocks;

import java.util.Random;

import al132.atmrockhounding.enums.EnumAlloy;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class AlloyBricks extends BaseMetaBlock {
	public static final PropertyEnum<EnumAlloy> VARIANT = PropertyEnum.create("type", EnumAlloy.class);
	Random rand = new Random();
	
    public AlloyBricks(Material material, String[] array, float hardness, float resistance, String name, SoundType stepSound){
        super(material, array, hardness, resistance, name, stepSound);
		setHarvestLevel("pickaxe", 0);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumAlloy.getValue(0)));
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, EnumAlloy.getValue(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		EnumAlloy type = (EnumAlloy) state.getValue(VARIANT);
		return type.ordinal();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

}
