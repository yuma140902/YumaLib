package yuma140902.mcmods.yumalib.internal.config;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import yuma140902.mcmods.yumalib.YumaLib;
import yuma140902.mcmods.yumalib.updatecheck.EnumUpdateChannel;

public class ModConfigCore {
	public static final String CATEGORY_GENERAL = "General";
	
	public static final String
		CONFIG_PROP_LANGKEY = "config.yumalib.prop.",
		CONFIG_CATEGORY_LANGKEY = "config.yumalib.category.";
	
	public static Configuration cfg;
	
	private static final Logger logger = LogManager.getLogger(YumaLib.MOD_NAME + "-Config");
	
	public static class Stats {
		static boolean _doUpdateCheck;
		public static boolean doUpdateCheck() {
			return _doUpdateCheck;
		}
		
		static String _updateChannel;
		public static EnumUpdateChannel updateChannel() {
			return EnumUpdateChannel.valueOf(_updateChannel);
		}
		
		static boolean debugMode;
		public static boolean isDebugMode() {
			return debugMode;
		}
	}
	
	public static void loadConfig(FMLPreInitializationEvent event) {
		cfg = new Configuration(event.getSuggestedConfigurationFile(), true);
		initConfig();
		syncConfig();
		wrapConfig();
	}
	
	private static void initConfig() {
		// General
		cfg.addCustomCategoryComment(CATEGORY_GENERAL, "Settings of YumaLib");
		cfg.setCategoryLanguageKey(CATEGORY_GENERAL, CONFIG_CATEGORY_LANGKEY + "general");
		cfg.setCategoryRequiresMcRestart(CATEGORY_GENERAL, true);
	}
	
	public static void syncConfig() {
		logger.info("Loading config");
		
		// General
		
		String key;
		ArrayList<String> order = new ArrayList<>();
		
		key = "Check for updates";
		order.add(key);
		Property doUpdateCheck = cfg.get(CATEGORY_GENERAL, key, true, "If true, the mod will check for updates automatically");
		Stats._doUpdateCheck = doUpdateCheck.getBoolean();
		
		key = "Update Channel";
		order.add(key);
		Property updateChannel = cfg.get(CATEGORY_GENERAL, key, EnumUpdateChannel.recommended.toString(), 
				"Channel of update checking", EnumUpdateChannel.valueStrings());
		Stats._updateChannel = updateChannel.getString();
		
		key = "Enable Debug Mode";
		order.add(key);
		Property debugMode = cfg.get(CATEGORY_GENERAL, key, false);
		Stats.debugMode = debugMode.getBoolean();
		
		cfg.getCategory(CATEGORY_GENERAL).setPropertyOrder(order);
		
		
		cfg.save();
	}
	
	private static void wrapConfig() {
	}
	
	public static String getSubCategory(String subCategory) {
		return CATEGORY_GENERAL + "." + subCategory;
	}
	
	public static String getCategoryLangkey(String key) {
		return CONFIG_CATEGORY_LANGKEY + key;
	}
	
	public static String getPropertyLangkey(String key) {
		return CONFIG_PROP_LANGKEY + key;
	}
}
