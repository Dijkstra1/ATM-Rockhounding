package al132.atmrockhounding.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class Utils {

	public static ItemStack damage(ItemStack inStack){
		ItemStack temp = inStack;
		temp.setItemDamage(inStack.getItemDamage()+1);

		if(temp.getItemDamage() >= temp.getMaxDamage()) return null;
		else return temp;
	}


	public static boolean isHandlerEmpty(ItemStackHandler handler){
		boolean output = true;
		for(int i = 0; i< handler.getSlots(); i++){
			if(handler.getStackInSlot(i) != null) output = false;
		}
		return output;
	}

	public static boolean areItemsEqualIgnoreMeta(ItemStack stack1, ItemStack stack2){
		if(stack1 == null && stack2 == null) return true;
		if(stack1 != null && stack2 != null){
			return stack1.getItem() == stack2.getItem();
		}
		return false;
	}

	public static ArrayList<Integer> intArrayToList(int[] array){
		ArrayList<Integer> temp = new ArrayList<Integer>(array.length);
		for(int i=0;i<array.length;i++){
			temp.add(array[i]);
		}
		return temp;
	}

	public static List<ItemStack> handlerToStackList(IItemHandler handler){
		ArrayList<ItemStack> temp = new ArrayList<ItemStack>();
		for(int i=0;i<handler.getSlots();i++){
			if(handler.getStackInSlot(i) != null){
				temp.add(handler.getStackInSlot(i));
			}
		}
		return temp;
	}


	public static boolean listContainsStack(ItemStack stack, List<ItemStack> list, boolean sizeSensitive){
		if(sizeSensitive) return list.stream().anyMatch(listStack ->
		ItemStack.areItemsEqual(stack, listStack) && listStack.stackSize >= stack.stackSize);

		else return list.stream().anyMatch(listStack -> ItemStack.areItemsEqual(stack, listStack));
	}

	public static boolean listContainsStackList(List<ItemStack> smallStack, List<ItemStack> largeStack, boolean sizeSensitive){
		int matchingStacks = 0;
		for(ItemStack s: smallStack){
			if(listContainsStack(s,largeStack,sizeSensitive)) matchingStacks++;
		}
		return matchingStacks == smallStack.size();
	}

	public static void decrementSlot(IItemHandlerModifiable handler, int slot){
		ItemStack temp = handler.getStackInSlot(slot);
		temp.stackSize--;
		if(temp.stackSize <= 0) handler.setStackInSlot(slot, null);
		else handler.setStackInSlot(slot, temp);
	}
	
	public static ArrayList<ItemStack> getOres(String name, int quantity){
		ArrayList<ItemStack> listToReturn = new ArrayList<ItemStack>();
		
		List<ItemStack> oreStacks = OreDictionary.getOres(name);
		for(ItemStack stack: oreStacks){
			ItemStack tempStack = stack.copy();
			tempStack.stackSize = quantity;
			listToReturn.add(tempStack);
		}
		return listToReturn;
	}


	public static List<ItemStack> getMatchingOreStacks(ItemStack stack, boolean includeParamStack){
		ArrayList<ItemStack> stacksToReturn = new ArrayList<ItemStack>();
		ArrayList<String> oreNames = new ArrayList<String>();

		if(includeParamStack) stacksToReturn.add(stack);
		int[] oreIds = OreDictionary.getOreIDs(stack);
		for(int i=0;i<oreIds.length;i++){
			oreNames.add(OreDictionary.getOreName(oreIds[i]));
		}
		for(String oreName: oreNames){
			for(ItemStack s: OreDictionary.getOres(oreName)){
				ItemStack tempStack = s.copy();
				tempStack.stackSize = stack.stackSize;
				stacksToReturn.add(tempStack);
			}
			stacksToReturn.addAll(OreDictionary.getOres(oreName));
		}
		
		if(stacksToReturn.size()>0) return stacksToReturn;
		else return null;
	}
	
}
