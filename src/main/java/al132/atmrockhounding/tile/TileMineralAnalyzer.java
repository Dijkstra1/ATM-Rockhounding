package al132.atmrockhounding.tile;

import al132.atmrockhounding.ModConfig;
import al132.atmrockhounding.Reference;
import al132.atmrockhounding.blocks.ModBlocks;
import al132.atmrockhounding.client.gui.GuiMineralAnalyzer;
import al132.atmrockhounding.enums.EnumFluid;
import al132.atmrockhounding.fluids.ModFluids;
import al132.atmrockhounding.items.ModItems;
import al132.atmrockhounding.recipes.ModRecipes;
import al132.atmrockhounding.recipes.ProbabilityStack;
import al132.atmrockhounding.utils.FuelUtils;
import al132.atmrockhounding.utils.Utils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import scala.reflect.internal.Trees.This;

public class TileMineralAnalyzer extends TileMachine implements IFluidHandlingTile{

	public static final int consumedSulf = 10;
	public static final int consumedChlo = 30;
	public static final int consumedFluo = 20;

	private static final int INPUT_SLOT = 0;
	//						 FUEL_SLOT = 1;
	private static final int CONSUMABLE_SLOT = 2;
	private static final int SULFUR_SLOT = 3;
	private static final int CHLOR_SLOT = 4;
	private static final int FLUO_SLOT = 5;
	private static final int INDUCTOR_SLOT = 6;

	private static final int OUTPUT_SLOT = 0;

	public FluidTank sulfTank;
	public FluidTank chloTank;
	public FluidTank fluoTank;

	public TileMineralAnalyzer() {
		super(7,1,1);

		sulfTank = new FluidTank(Fluid.BUCKET_VOLUME*10){
			@Override  
			public boolean canFillFluidType(FluidStack fluid)
		    {
		        return fluid.getFluid() == ModFluids.SULFURIC_ACID;
		    }

		};
		sulfTank.setTileEntity(this);
		sulfTank.setCanDrain(false);

		chloTank = new FluidTank(Fluid.BUCKET_VOLUME*10){
			@Override  
			public boolean canFillFluidType(FluidStack fluid)
		    {
		        return fluid.getFluid() == ModFluids.HYDROCHLORIC_ACID;
		    }

		};
		
		chloTank.setTileEntity(this);
		chloTank.setCanDrain(false);

		fluoTank = new FluidTank(Fluid.BUCKET_VOLUME*10){
			@Override  
			public boolean canFillFluidType(FluidStack fluid)
		    {
		        return fluid.getFluid() == ModFluids.HYDROFLUORIC_ACID;
		    }

		};
		
		fluoTank.setTileEntity(this);
		fluoTank.setCanDrain(false);

		input =  new MachineStackHandler(7,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){

				if(slot == INPUT_SLOT && insertingStack.getMetadata() != 0 && Utils.areItemsEqualIgnoreMeta(new ItemStack(ModBlocks.mineralOres), insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && FuelUtils.isItemFuel(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CONSUMABLE_SLOT && insertingStack.getItem() == ModItems.testTube){
					return super.insertItem(slot, insertingStack, simulate);
				}
				 if(slot == INDUCTOR_SLOT && ItemStack.areItemsEqual(insertingStack, Reference.inductor)){
					 return super.insertItem(slot, insertingStack, simulate);
				 }

				 return insertingStack;
			}
		};

		this.automationInput = new WrappedItemHandler(input, WrappedItemHandler.WriteMode.IN_OUT);
	}

	private boolean isOutputEmpty(){
		return output.getStackInSlot(OUTPUT_SLOT) == null;
	}

	private boolean hasConsumable(){
		return !(input.getStackInSlot(CONSUMABLE_SLOT) == null);
	}

	private boolean hasInput(){
		return !(input.getStackInSlot(INPUT_SLOT) == null);
	}

	//----------------------- CUSTOM -----------------------

	public int getCookTimeMax(){
		return ModConfig.speedAnalyzer;
	}

	//----------------------- I/O -----------------------

	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.cookTime = compound.getInteger("CookTime");
		this.sulfTank.readFromNBT(compound.getCompoundTag("SulfTank"));
		this.chloTank.readFromNBT(compound.getCompoundTag("ChloTank"));
		this.fluoTank.readFromNBT(compound.getCompoundTag("FluoTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("CookTime", this.cookTime);
		
		NBTTagCompound sulfTankNBT = new NBTTagCompound();
		this.sulfTank.writeToNBT(sulfTankNBT);
		compound.setTag("SulfTank", sulfTankNBT);

		
		NBTTagCompound chloTankNBT = new NBTTagCompound();
		this.chloTank.writeToNBT(chloTankNBT);
		compound.setTag("ChloTank", chloTankNBT);

		
		NBTTagCompound fluoTankNBT = new NBTTagCompound();
		this.fluoTank.writeToNBT(fluoTankNBT);
		compound.setTag("FluoTank", fluoTankNBT);

		return compound;
	}

	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(input.getStackInSlot(FUEL_SLOT) != null){fuelHandler();}
		if(!worldObj.isRemote){
			if(canAnalyze()){
				cookTime++; powerCount--;
				if(cookTime >= getCookTimeMax()) {
					cookTime = 0; 
					handleSlots();
				}
			}
			this.markDirtyClient();
		}
	}

	private void handleSlots() {
		calculateOutput(input.getStackInSlot(INPUT_SLOT).getItemDamage());
		input.damageSlot(CONSUMABLE_SLOT);
		input.decrementSlot(INPUT_SLOT);
	}


	private void calculateOutput(int entryMineral) {

		Item outItem = ModItems.arsenateShards;
		switch(entryMineral){
		case 1: outItem = ModItems.arsenateShards; break;
		case 2: outItem = ModItems.borateShards; break;
		case 3: outItem = ModItems.carbonateShards; break;
		case 4: outItem = ModItems.halideShards; break;
		case 5: outItem = ModItems.nativeShards; break;
		case 6: outItem = ModItems.oxideShards; break;
		case 7: outItem = ModItems.phosphateShards; break;
		case 8: outItem = ModItems.silicateShards; break;
		case 9: outItem = ModItems.sulfateShards; break;
		case 10: outItem = ModItems.sulfideShards; break;
		default: break;
		}
		handleOutput(outItem, 
				ProbabilityStack.calculateProbability(ModRecipes.analyzerRecipes.get(entryMineral-1).getProbabilityStack()).getItemDamage());
	}

	private void handleOutput(Item mineral, int meta) {
		ItemStack outputStack = new ItemStack(mineral, rand.nextInt(ModConfig.maxMineral) + 1, meta);
		output.setStackInSlot(OUTPUT_SLOT, outputStack.copy()); //must be empty to process anyway, so no problem here
		this.sulfTank.getFluid().amount-= consumedSulf;
		this.chloTank.getFluid().amount-= consumedChlo;
		this.fluoTank.getFluid().amount-= consumedFluo;
	}

	private boolean canAnalyze() {
		return  output.getStackInSlot(OUTPUT_SLOT) == null
				&& hasInput()
				&& hasConsumable()
				&& powerCount >= getCookTimeMax()
				&& this.sulfTank.getFluidAmount() >= consumedSulf
				&& this.chloTank.getFluidAmount() >= consumedChlo
				&& this.fluoTank.getFluidAmount() >= consumedFluo;
	}


	@Override
	public boolean canInduct(){
		return !(input.getStackInSlot(INDUCTOR_SLOT) == null);
	}

	@Override
	public int getGUIHeight() {
		return GuiMineralAnalyzer.HEIGHT;
	}

	@Override
	public boolean interactWithBucket(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		boolean didFill = FluidUtil.interactWithFluidHandler(heldItem, this.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side), player);
		this.markDirtyClient();
		return didFill;
	}


	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return true;
		else return super.hasCapability(capability, facing);
	}

	public FluidHandlerConcatenate getCombinedTank(){
		return new FluidHandlerConcatenate(sulfTank,chloTank,fluoTank);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return (T) getCombinedTank();
		return super.getCapability(capability, facing);
	}
}