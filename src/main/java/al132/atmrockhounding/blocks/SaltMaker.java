package al132.atmrockhounding.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import al132.atmrockhounding.client.GuiHandler;
import al132.atmrockhounding.items.ModItems;
import al132.atmrockhounding.tile.TileSaltMaker;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SaltMaker extends BaseTileBlock implements IMetaBlockName{

	public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("type", SaltMaker.EnumType.class);
	private static final AxisAlignedBB BOUNDBOX = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3D, 1.0D);

	public static int saltAmount;

	public SaltMaker(float hardness, float resistance, String name) {
		super(name, Material.ROCK, TileSaltMaker.class, GuiHandler.NO_GUI);
		setHardness(hardness);
		setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
		setSoundType(SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.getValue(0)));
		this.useNeighborBrightness = true;
	}

	@Override
	public boolean isFullCube(IBlockState state){
		return false;
	}
	
	@Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta);
    }
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){}
	
   @Override
   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){}


	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, SaltMaker.EnumType.getValue(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).ordinal();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		list.add(new ItemStack(itemIn, 1, 0));
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos){
		return BOUNDBOX;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
		return BOUNDBOX;
	}

	@Override
	public String getSpecialName(ItemStack stack) {
		return SaltMaker.EnumType.values()[stack.getItemDamage()].toString();
	}

	public int damageDropped(IBlockState state){
		return 0;
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player){
		return false;
	}

	

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer(){
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
		if(side == EnumFacing.UP){
			EnumType type = (EnumType) state.getValue(VARIANT);
			if(type.ordinal() == 0 && heldItem != null && heldItem.getItem() == Items.WATER_BUCKET){
				heldItem.stackSize--;
				playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
				if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.BUCKET))){
					playerIn.dropItem(new ItemStack(Items.BUCKET), false);
				}
				state = this.getStateFromMeta(1);
				worldIn.setBlockState(pos, state);
			}else if(type.ordinal() == 1 && heldItem != null && heldItem.getItem() == Items.BUCKET){
				heldItem.stackSize--;
				playerIn.playSound(SoundEvents.ITEM_BUCKET_EMPTY, 1.0F, 1.0F);
				if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET))){
					playerIn.dropItem(new ItemStack(Items.WATER_BUCKET), false);
				}
				state = this.getStateFromMeta(0);
				worldIn.setBlockState(pos, state);
			}else if(type.ordinal() == 6 && heldItem != null && heldItem.getItem() instanceof ItemSpade){
				state = this.getStateFromMeta(0);
				worldIn.setBlockState(pos, state);
				playerIn.playSound(SoundEvents.BLOCK_GRAVEL_HIT, 0.5F, 1.5F);
				if(!worldIn.isRemote) {
					int getSalt = new Random().nextInt(saltAmount) + 1; 
					dropItemStack(worldIn, new ItemStack(ModItems.salt, getSalt, 1), pos);
				}
				if(!playerIn.capabilities.isCreativeMode){
					int damageItem = heldItem.getItemDamage() + 1;
					heldItem.setItemDamage(damageItem);
					if(damageItem >= heldItem.getMaxDamage()){heldItem.stackSize--;}
				}
			}
		}
		return false;
	}

	private void dropItemStack(World worldIn, ItemStack itemStack, BlockPos pos) {
		EntityItem entityitem = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemStack);
		entityitem.setPosition(pos.getX(), pos.getY() + 0.5D, pos.getZ());
		worldIn.spawnEntityInWorld(entityitem);
	}

	
	
	
	
	public enum EnumType implements IStringSerializable{
		EMPTY	(),
		WATER	(),
		STEPA	(),
		STEPB	(),
		STEPC	(),
		STEPD	(),
		SALT	();


		public static EnumType getValue(int index){
			return EnumType.values()[index];
		}

		@Override
		public String getName() {
			return this.toString().toLowerCase();
		}

	}

}