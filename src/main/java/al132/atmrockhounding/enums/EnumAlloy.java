package al132.atmrockhounding.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumAlloy implements IStringSerializable {
	CUBE				(),
	SCAL				(),
	BAM					(),
	YAG					(),
	CUPRONICKEL			(),
	NIMONIC				(),
	HASTELLOY			(),
	NICHROME			(),
	MISCHMETAL			(),
	ROSEGOLD			(),
	GREENGOLD			(),
	WHITEGOLD			(),
	SHIBUICHI			(),
	TOMBAK				(),
	PEWTER				(),
	CORTEN				();

	public static int size(){
		return EnumAlloy.values().length;
	}
	
	public static String[] getLowerNameArray(){
		String[] temp = new String[size()];
		for(int i=0; i<size();i++){
			temp[i] = EnumAlloy.getName(i);
		}
		return temp;
	}

	public static String[] getOreArray(String prefix){
		String[]temp = new String[size()];
		for(int i=0; i<size();i++){
			temp[i] = getDictName(prefix,i);
		}
		return temp;
	}

	public static String getDictName(String prefix, int index){
		return prefix + getValue(index).getName().substring(0,1).toUpperCase() + getValue(index).getName().substring(1);
	}
	
	public static EnumAlloy getValue(int index){
		return EnumAlloy.values()[index];
	}
	
	public static String getStandardName(int index){
		return EnumAlloy.getName(index).substring(0,1).toUpperCase() + EnumAlloy.getName(index).substring(1);
	}


	@Override
	public String getName() {
		return this.toString().toLowerCase();
	}
	
	public static String getName(int index){
		return EnumAlloy.values()[index].getName();
	}

}
