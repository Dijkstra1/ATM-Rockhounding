package al132.atmrockhounding.tile;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileBase extends TileEntity {

	public boolean canInteractWith(EntityPlayer playerIn) {
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return (oldState.getBlock() != newState.getBlock());
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{        
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		
		return new SPacketUpdateTileEntity(pos, 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		this.readFromNBT(packet.getNbtCompound());
	}

	public int getInventoryStackLimit(){ 
		return 64; 
	}

	//Courtesy of mcjtylib
	public void markDirtyClient() {
		markDirty();
		if (worldObj != null) {
			IBlockState state = worldObj.getBlockState(getPos());
			worldObj.notifyBlockUpdate(getPos(), state, state, 3);
		}
	}
}
