package al132.atmrockhounding.tile;

import com.google.common.collect.Range;

import al132.atmrockhounding.ModConfig;
import al132.atmrockhounding.blocks.ModBlocks;
import al132.atmrockhounding.blocks.SaltMaker;
import al132.atmrockhounding.blocks.SaltMaker.EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class TileSaltMaker extends TileBase implements ITickable{
	private int evaporateCount = -1;
	public static int evaporationMultiplier;
	private int evaporationSpeed = 600 * evaporationMultiplier;

	@Override
	public void update() {
		if(!worldObj.isRemote){
		    IBlockState state = worldObj.getBlockState(pos);
			Biome biome = worldObj.getBiomeGenForCoords(pos);
			EnumType type = (EnumType) state.getValue(SaltMaker.VARIANT);

		    //fill tank on rain
			if(type.ordinal() == 0){
				evaporateCount = -1;
				if(canRainRefill(biome)){
	  		    	state = ModBlocks.saltMaker.getStateFromMeta(1);
					worldObj.setBlockState(pos, state);
				}
			}
			
			//lose progress on rain
			if(type.ordinal() > 0){
				if(canRainMelt(biome)){
					evaporateCount = -1;
	  		    	state = ModBlocks.saltMaker.getStateFromMeta(1);
					worldObj.setBlockState(pos, state);
				}
			}

			//do evaporation
			if(Range.closed(0, 6).contains(type.ordinal())){
				if(evaporateCount >= calculatedEvaporation(biome)){
					evaporateCount = -1;
	  		    	state = ModBlocks.saltMaker.getStateFromMeta(type.ordinal() + 1);
					worldObj.setBlockState(pos, state);
				}else{
					if(canEvaporate()){
						evaporateCount++;
					}else{
						if(canRainRefill(biome)){
							evaporateCount = -1;
			  		    	state = ModBlocks.saltMaker.getStateFromMeta(1);
							worldObj.setBlockState(pos, state);
						}
					}
				}
			}

		}
	}

	private boolean canEvaporate() {
		return worldObj.isDaytime() && worldObj.canSeeSky(pos) && !worldObj.isRaining();
	}

	private int calculatedEvaporation(Biome biome) {
		if(BiomeDictionary.isBiomeOfType(biome, Type.SANDY)){
			return evaporationSpeed / 2 ;
		}else if(BiomeDictionary.isBiomeOfType(biome, Type.WET) || BiomeDictionary.isBiomeOfType(biome, Type.WATER) || BiomeDictionary.isBiomeOfType(biome, Type.FOREST) || BiomeDictionary.isBiomeOfType(biome, Type.CONIFEROUS) || BiomeDictionary.isBiomeOfType(biome, Type.SWAMP)){
			return evaporationSpeed * 2 ;
		}else if(BiomeDictionary.isBiomeOfType(biome, Type.COLD) || BiomeDictionary.isBiomeOfType(biome, Type.MOUNTAIN)){
			return evaporationSpeed * 4 ;
		}else{
			return evaporationSpeed;
		}
	}

	private boolean canRainMelt(Biome biome) {
		return worldObj.isRaining() && !BiomeDictionary.isBiomeOfType(biome, Type.SANDY);
	}

	private boolean canRainRefill(Biome biome) {
		return worldObj.isRaining() && ModConfig.enableRainRefill && !BiomeDictionary.isBiomeOfType(biome, Type.SANDY);
	}

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        this.evaporateCount = compound.getInteger("EvaporateCount");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("EvaporateCount", this.evaporateCount);
        NBTTagList nbttaglist = new NBTTagList();
        compound.setTag("Items", nbttaglist);
        return compound;
    }
}