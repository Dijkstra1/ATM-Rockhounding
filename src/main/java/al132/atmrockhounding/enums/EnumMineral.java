package al132.atmrockhounding.enums;

public enum EnumMineral {

	UNINSPECTED,
	ARSENATE,
	BORATE,
	CARBONATE,
	HALIDE,
	NATIVE,
	OXIDE,
	PHOSPHATE,
	SILICATE,
	SULFATE,
	SULFIDE;
	
	public static String getName(int index){
		return EnumMineral.values()[index].getName();
	}
	
	public String getName(){
		return this.toString().toLowerCase();
	}
	
	public static int size(){
		return EnumMineral.values().length;
	}
}

