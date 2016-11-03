package al132.atmrockhounding.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFluidContainer extends ItemBase {


	public ItemFluidContainer(String name) {
		super(name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> tooltip, boolean held) {
		if(itemstack.hasTagCompound()) {
			String blockName = itemstack.getTagCompound().getString("Block");
			tooltip.add(TextFormatting.DARK_GRAY + "Contained Fluid" + ": " + TextFormatting.YELLOW + blockName);
		}
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack)){
			return EnumActionResult.FAIL;
		}else{
			pos = pos.offset(facing);
			BlockPos basePos = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
			if (worldIn.isAirBlock(pos) && worldIn.isSideSolid(basePos, EnumFacing.UP)){
				if(stack.hasTagCompound()){
					String blockName = stack.getTagCompound().getString("Block");
					if(blockName != ""){
						IBlockState block = Block.getBlockFromName(blockName).getDefaultState();
						worldIn.setBlockState(pos, block);
					}
				}
				if (!playerIn.capabilities.isCreativeMode){
					--stack.stackSize;
				}
			}
		}
		return EnumActionResult.PASS;
	}
}