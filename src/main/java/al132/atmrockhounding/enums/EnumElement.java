package al132.atmrockhounding.enums;

public enum EnumElement {
	CERIUM(),
	DYSPROSIUM(),
	ERBIUM(),
	EUROPIUM(),
	GADOLINIUM(),
	HOLMIUM(),
	LANTHANUM(),
	LUTETIUM(),
	NEODYMIUM(),
	PRASEODYMIUM(),
	SAMARIUM(),
	SCANDIUM(),
	TERBIUM(),
	THULIUM(),
	YTTERBIUM(),
	YTTRIUM(),
	IRON(),
	COPPER(),
	TIN(),
	LEAD(),
	ZINC(),
	CHROMIUM(),
	BORON(),
	SILVER(),
	ALUMINUM(),
	MANGANESE(),
	NICKEL(),
	COBALT(),
	MAGNESIUM(),
	TITANIUM(),
	SODIUM(),
	THORIUM(),
	CALCIUM(),
	PHOSPHORUS(),
	LITHIUM(),
	POTASSIUM(),
	BERYLLIUM(),
	SULFUR(),
	BISMUTH(),
	NIOBIUM(),
	TANTALUM(),
	ARSENIC(),
	SILICON(),
	URANIUM(),
	PLATINUM(),
	GOLD(),
	TUNGSTEN(),
	OSMIUM(),
	IRIDIUM(),
	CADMIUM();
	
	
	EnumElement(){
	}
	
	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getName(i);
		}
		return temp;
	}
	
	public static String getDustName(int index){
		return "dust" + getName(index).substring(0,1).toUpperCase() + getName(index).substring(1);
	}
	
	public String getDustName(){
		return this.getDustName(this.ordinal());
	}
	
	public static String getIngotName(int index){
		return "ingot" + getName(index).substring(0,1).toUpperCase() + getName(index).substring(1);
	}
	
	public static String getName(int index){
		return EnumElement.values()[index].toString().toLowerCase();
	}
	
	public static int size(){
		return values().length;
	}

}
