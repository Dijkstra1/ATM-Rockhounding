package al132.atmrockhounding.blocks;

import al132.atmrockhounding.client.GuiHandler;
import al132.atmrockhounding.tile.TileMineralAnalyzer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;

public class MineralAnalyzer extends BaseTileBlock{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public MineralAnalyzer(float hardness, float resistance, String name){
        super(name, Material.IRON, TileMineralAnalyzer.class, GuiHandler.mineralAnalyzerID);
		setHardness(hardness);
		setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }
}