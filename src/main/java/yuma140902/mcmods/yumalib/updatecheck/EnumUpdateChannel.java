package yuma140902.mcmods.yumalib.updatecheck;

public enum EnumUpdateChannel {
	recommended,
	latest; 
	
	public static String[] valueStrings() {
		String[] list = new String[values().length];
		for(int i = 0; i < list.length; ++i) {
			list[i] = values()[i].toString();
		}
		return list;
	}
}
