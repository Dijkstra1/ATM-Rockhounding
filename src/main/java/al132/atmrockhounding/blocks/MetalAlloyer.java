package al132.atmrockhounding.blocks;

import al132.atmrockhounding.client.GuiHandler;
import al132.atmrockhounding.tile.TileMetalAlloyer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;

public class MetalAlloyer extends BaseTileBlock{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public MetalAlloyer(float hardness, float resistance, String name){
		super(name, Material.IRON, TileMetalAlloyer.class,GuiHandler.metalAlloyerID);
		setHardness(hardness); setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
}