package al132.atmrockhounding.blocks;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import al132.atmrockhounding.ATMRockhounding;
import al132.atmrockhounding.tile.IFluidHandlingTile;
import al132.atmrockhounding.tile.TileBase;
import al132.atmrockhounding.tile.TileChemicalExtractor;
import al132.atmrockhounding.tile.TileInv;
import al132.atmrockhounding.tile.TileMachine;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BaseTileBlock extends BaseBlock implements ITileEntityProvider {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	final Class<? extends TileEntity> tileClass;
	public int guiID;

	public BaseTileBlock(String name, Material material, Class<? extends TileEntity> tileClass, int guiID) {
		super(name, material);
		this.tileClass = tileClass;
		this.guiID = guiID;
		setSoundType(SoundType.METAL);
		String tileName = name.substring(0, 1).toUpperCase() + name.substring(1);
		GameRegistry.registerTileEntity(tileClass, tileName);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		try {
			return tileClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player,
			boolean willHarvest) {
		if (willHarvest)
			return true;
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te,
			ItemStack stack) {
		super.harvestBlock(world, player, pos, state, te, stack);
		world.setBlockToAir(pos);
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		TileEntity tile = world.getTileEntity(pos);
		ArrayList<ItemStack> temp = new ArrayList<ItemStack>();
		ItemStack ownStack = new ItemStack(Item.getItemFromBlock(this));
		NBTTagCompound tag = new NBTTagCompound();

		if (tile instanceof TileInv) {
			if (tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
				IItemHandler inventory = ((TileInv) tile).getInventory();
				int slots = inventory.getSlots();
				for (int i = 0; i < slots; i++) {
					if (inventory.getStackInSlot(i) != null) {
						temp.add(inventory.getStackInSlot(i));
					}
				}
			}
		}
		if (tile instanceof TileMachine) {
			IEnergyStorage energyTank = ((TileMachine) tile).getEnergyStorage();
			if (energyTank != null) {
				int powerCount = energyTank.getEnergyStored();
				if (powerCount > 0)
					tag.setInteger("PowerCount", powerCount);

			}
		}

		if (tile instanceof TileChemicalExtractor) {
			tag.setIntArray("elements", ((TileChemicalExtractor) tile).getElements());
		}

		ownStack.setTagCompound(tag);
		temp.add(ownStack);

		return temp;

	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			@Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if (world.getTileEntity(pos) instanceof IFluidHandlingTile) {
				if (heldItem != null) {
					if (heldItem.getItem() instanceof ItemBucket || heldItem.getItem() instanceof UniversalBucket) {
						((IFluidHandlingTile) world.getTileEntity(pos)).interactWithBucket(world, pos, state, player,
								hand, heldItem, side, hitX, hitY, hitZ);

						return true;
					}
				}
			}

			player.openGui(ATMRockhounding.instance, guiID, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing()), 2);
		NBTTagCompound tag = stack.getTagCompound();
		if (tag != null) {
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof TileMachine) {
				// te.writeToNBT(tag);
				((TileMachine) te).getEnergyStorage().receiveEnergy(tag.getInteger("PowerCount"), false);
			}
			if (te instanceof TileChemicalExtractor) {
				int[] elementList = tag.getIntArray("elements");
				((TileChemicalExtractor) te).setElements(elementList);
			}
		}

	}


	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		return Container.calcRedstone(worldIn.getTileEntity(pos));
	}

	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);
		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}
		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

}