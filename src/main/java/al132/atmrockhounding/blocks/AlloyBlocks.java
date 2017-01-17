package al132.atmrockhounding.blocks;

import java.util.Random;

import al132.atmrockhounding.enums.EnumAlloy;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AlloyBlocks extends BaseMetaBlock {
	public static final PropertyEnum<EnumAlloy> VARIANT = PropertyEnum.create("type", EnumAlloy.class);
	Random rand = new Random();

	public AlloyBlocks(Material material, String[] array, float hardness, float resistance, String name, SoundType stepSound){
		super(material, array, hardness, resistance, name, stepSound);
		System.out.println(array.length);

		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumAlloy.getValue(0)));
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta);
	}

	@Override
	public String getSpecialName(ItemStack stack) {
		return this.array[stack.getItemDamage()];
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, EnumAlloy.getValue(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumAlloy)state.getValue(VARIANT)).ordinal();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, VARIANT);
	}


	/*@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
		for (EnumAlloy alloy : EnumAlloy.values()) {
			if(alloy.ordinal() < 16){
				list.add(new ItemStack(item, 1, alloy.ordinal()));
			}
		}
	}*/


}