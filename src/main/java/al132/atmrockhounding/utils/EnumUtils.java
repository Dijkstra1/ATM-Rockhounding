package al132.atmrockhounding.utils;

public class EnumUtils {

	
	public static <T extends Enum<T>> int size(Class<T> enumClass){
		return enumClass.getEnumConstants().length;
	}
	
	public static <T extends Enum<T>> String getName(Class<T> enumClass, int index){
		return get(enumClass,index).toString().toLowerCase();
	}
	
	public static <T extends Enum<T>> T get(Class<T> enumClass, int index){
		return enumClass.getEnumConstants()[index];
	}
	
	public static <T extends Enum<T>> String[] getNameArray(Class<T> enumClass){
		String[] temp = new String[size(enumClass)];
		for(int i=0; i<size(enumClass); i++){
			temp[i] = getName(enumClass, i);
		}
		return temp;
	}

}
