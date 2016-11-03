package al132.atmrockhounding.items;

import java.util.List;

import al132.atmrockhounding.enums.EnumFluid;
import al132.atmrockhounding.tile.TileLabOven;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChemicalItems extends ItemMetaBase {

	private int fillTank;

	public ChemicalItems(String name, String[] array) {
		super(name,array);
	}
	/*
	@Override
	public int getItemStackLimit(ItemStack stack) {
		if(stack.hasTagCompound()){
			if(stack.getItemDamage() == 0){
				if(!stack.getTagCompound().getString("Fluid").equals(EnumFluid.EMPTY.getName())){
					return 1;
				}
			}
		}
		return stack.getMaxStackSize();
	}*/

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for(int i = 0; i < itemArray.length; i++){subItems.add(new ItemStack(itemIn, 1, i));}
		for(int x = 1; x < EnumFluid.values().length; x++){
			ItemStack tank = new ItemStack(itemIn, 1, 0);
			subItems.add(makeTanks(tank, EnumFluid.values()[x].getName(), TileLabOven.tankMax));
		}
	}

	public static ItemStack makeTanks(ItemStack itemstack, String fluidName, int capacity) {
		if(!itemstack.hasTagCompound()) {
			setItemNbt(itemstack);
		}
		itemstack.getTagCompound().setString("Fluid", fluidName);
		itemstack.getTagCompound().setInteger("Quantity", capacity);
		return itemstack.copy();
	}

	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
		if(itemstack.getItemDamage() == 0){
			setItemNbt(itemstack);
		}
	}

	private static void setItemNbt(ItemStack itemstack) {
		if(itemstack.getItemDamage() == 0){
			itemstack.setTagCompound(new NBTTagCompound());
			itemstack.getTagCompound().setString("Fluid", EnumFluid.EMPTY.getName());
			itemstack.getTagCompound().setInteger("Quantity", 0);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> tooltip, boolean held) {
		if(itemstack.getItemDamage() == 0){
			if(itemstack.hasTagCompound()) {
				String fluid = itemstack.getTagCompound().getString("Fluid");
				int quantity = itemstack.getTagCompound().getInteger("Quantity");
				tooltip.add(TextFormatting.DARK_GRAY + "Fluid" + ": " + TextFormatting.YELLOW + fluid);
				tooltip.add(TextFormatting.DARK_GRAY + "Quantity" +": " + TextFormatting.WHITE + quantity + "/" + TileLabOven.tankMax + " beakers");
			}else{
				setItemNbt(itemstack);
			}
		}
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected){
		if(itemstack.getItemDamage() == 0){
			if(itemstack.hasTagCompound()) {
				if(isSelected){
					String fluid = itemstack.getTagCompound().getString("Fluid");
					int quantity = itemstack.getTagCompound().getInteger("Quantity");
					if(fluid.matches(EnumFluid.EMPTY.getName()) || fluid.matches(EnumFluid.WATER.getName())){
						if(quantity < TileLabOven.tankMax){
							if(entityIn != null && entityIn instanceof EntityPlayer){
								EntityPlayer entityplayer = (EntityPlayer)entityIn;
								if(entityplayer.isInWater()){
									if(fillTank >= 40){
										quantity++;
										itemstack.getTagCompound().setInteger("Quantity", quantity);
										if(fluid.matches(EnumFluid.EMPTY.getName())){itemstack.getTagCompound().setString("Fluid", EnumFluid.WATER.getName());}
										fillTank = 0;
										entityplayer.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
										BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
										if(!world.isRemote){
											blockPos.setPos(entityplayer.posX, entityplayer.posY, entityplayer.posZ);
											if(world.getBlockState(blockPos).getBlock() == Blocks.WATER){world.setBlockToAir(blockPos);}
											blockPos.setPos(entityplayer.posX, entityplayer.posY + 1, entityplayer.posZ);
											if(world.getBlockState(blockPos).getBlock() == Blocks.WATER){world.setBlockToAir(blockPos);}
										}
									}else{
										fillTank++;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemstack, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(itemstack.getItemDamage() == 0){
			if(itemstack.hasTagCompound()) {
				String fluid = itemstack.getTagCompound().getString("Fluid");
				int quantity = itemstack.getTagCompound().getInteger("Quantity");
				if(fluid.matches(EnumFluid.SULFURIC_ACID.getName())){
					if(quantity > 0){
						quantity--;
						itemstack.getTagCompound().setInteger("Quantity", quantity);
						playerIn.playSound(SoundEvents.ITEM_BUCKET_EMPTY, 1.0F, 1.0F);
						if(!worldIn.isRemote){
							//if(!playerIn.inventory.addItemStackToInventory(new ItemStack(ModItems.miscItems,1,29))){ //TODO what the shit is miscItems,29?
							//	playerIn.dropItem(new ItemStack(ModItems.miscItems,1,29), false);
							// }
						}
					}
				}
			}
		}
		return super.onItemRightClick(itemstack, worldIn, playerIn, hand);
	}
}