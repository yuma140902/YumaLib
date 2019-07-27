package yuma140902.mcmods.yumalib.updatecheck;

public enum EnumUpdateChannel {
	Recommended("recommended"),
	Latest("latest"); 
	
	private String str;

	private EnumUpdateChannel(String str) {
		this.str = str;
	}
	
	@Override
	public String toString() {
		return str;
	}
	
	public static String[] valueStrings() {
		String[] list = new String[values().length];
		for(int i = 0; i < list.length; ++i) {
			list[i] = values()[i].toString();
		}
		return list;
	}
}
