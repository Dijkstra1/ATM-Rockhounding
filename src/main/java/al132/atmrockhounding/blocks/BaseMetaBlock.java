package al132.atmrockhounding.blocks;

import java.util.List;
import java.util.Random;

import al132.atmrockhounding.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BaseMetaBlock extends Block implements IMetaBlockName{
	public String[] array;
	
	protected BaseMetaBlock(Material material, String[]array, float hardness, float resistance, String name, SoundType stepSound) {
		super(material);
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		setCreativeTab(Reference.RockhoundingChemistry);
		setHardness(hardness);
		setResistance(resistance);	
		setSoundType(stepSound);
		//setHarvestLevel("pickaxe", 1);
		this.array = array;
	}

	@Override
	public String getSpecialName(ItemStack stack) {
		return this.array[stack.getItemDamage()];
	}

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
		return Item.getItemFromBlock(this);
	}

	@Override
    public int damageDropped(IBlockState state){
    	return getMetaFromState(state);
    }

    @Override
	public int quantityDropped(Random rand) {
		return 1;
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for (int i = 0; i < array.length; i++){
			list.add(new ItemStack(itemIn, 1, i));
		}
	}

}
