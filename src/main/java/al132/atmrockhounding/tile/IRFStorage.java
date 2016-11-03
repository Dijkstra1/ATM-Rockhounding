package al132.atmrockhounding.tile;

import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.IEnergyStorage;
import net.minecraft.util.EnumFacing;

public interface IRFStorage extends IEnergyReceiver, IEnergyStorage{


	abstract int getPower();
	abstract int getPowerMax();
	abstract int getRedstoneMax();
	abstract int getRedstone();
	abstract void setPower(int amount);
	abstract void addPower(int amount);
	abstract void setRedstone(int amount);
	abstract void addRedstone(int amount);

	public default boolean hasPower(){
		return getPower() > 0;
	}
	abstract boolean canInduct();

	// COFH SUPPORT
	public default int getEnergyStored(EnumFacing from) {
		if(canInduct())return getPower();
		else return 0;
	}

	@Override
	public default int getMaxEnergyStored(EnumFacing from) { return getPowerMax(); }

	@Override
	public default boolean canConnectEnergy(EnumFacing from){ return true; }

	@Override
	public default int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		int received = 0;
		if(canInduct()){
			if (getPower() == -1) return 0;
			received = Math.min(getPowerMax() - getPower(), maxReceive);
			if (!simulate) {
				addPower(received);
				if(getPower() >= getPowerMax()) setPower(getPowerMax());
			}
		}else{
			if (getRedstone() == -1) return 0;
			received = Math.min(getRedstoneMax() - getRedstone(), maxReceive);
			if (!simulate) {
				addRedstone(received);
				if(getRedstone() >= getRedstoneMax()) setRedstone(getRedstoneMax());
			}
		}
		return received;
	}



	// FORGE ENERGY SUPPORT
	@Override
	public default int receiveEnergy(int maxReceive, boolean simulate) {
		int received = 0;
		if(canInduct()){
			if (getPower() == -1) return 0;
			received = Math.min(getPowerMax() - getPower(), maxReceive);
			if (!simulate) {
				addPower(received);
				if(getPower() >= getPowerMax()) setPower(getPowerMax());
			}
			else{
				if (getRedstone() == -1) return 0;
				received = Math.min(getRedstoneMax() - getRedstone(), maxReceive);
				if (!simulate) {
					addRedstone(received);
					if(getRedstone() >= getRedstoneMax()) setRedstone(getRedstoneMax());
				}
			}
		}
		return received;
	}

	@Override
	public default int extractEnergy(int maxExtract, boolean simulate) { return 0; }

	@Override
	public default int getEnergyStored() {
		if(canInduct()) return getPower();
		else return getRedstone();
	}

	@Override
	public default int getMaxEnergyStored() {
		if(canInduct()) return getPowerMax();
		else return getRedstoneMax();
	}
/*
	@Override
	public default boolean canExtract() {return false;}

	@Override
	public default boolean canReceive() {return true;}*/
}
