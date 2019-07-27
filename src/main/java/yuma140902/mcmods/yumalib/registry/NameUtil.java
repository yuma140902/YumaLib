package yuma140902.mcmods.yumalib.registry;

public class NameUtil {
	
	public final String TEXTURE_DOMAIN;
	public final String UNLOCALIZED_ENTRY_DOMAIN;
	
	public NameUtil(String textureDomain, String unlocalizedEntryDomain) {
		this.TEXTURE_DOMAIN = textureDomain;
		this.UNLOCALIZED_ENTRY_DOMAIN = unlocalizedEntryDomain;
	}
	
	public String domainedModTextureName(String name) {
		return domainedTextureName(TEXTURE_DOMAIN, name);
	}
	
	public String domainedUnlocalizedName(String name) {
		return domainedUnlocalizedName(UNLOCALIZED_ENTRY_DOMAIN, name);
	}
	
	
	public static String domainedMCTextureName(String name) {
		return domainedTextureName("minecraft", name);
	}
	
	public static String domainedTextureName(String textureDomain, String name) {
		return textureDomain + ":" + name;
	}
	
	public static String domainedUnlocalizedName(String unlocalizedEntryDomain, String name) {
		return unlocalizedEntryDomain + "." + name;
	}
}
