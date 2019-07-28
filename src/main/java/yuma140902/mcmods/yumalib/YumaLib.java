package yuma140902.mcmods.yumalib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import yuma140902.mcmods.yumalib.internal.YumaLibStat;
import yuma140902.mcmods.yumalib.internal.config.ModConfigCore;
import yuma140902.mcmods.yumalib.internal.proxy.CommonProxy;
import yuma140902.mcmods.yumalib.network.NoteBlockPlayHandler;
import yuma140902.mcmods.yumalib.network.NoteBlockPlayMessage;
import yuma140902.mcmods.yumalib.updatecheck.UpdateChecker;

@Mod(modid = YumaLib.MOD_ID, name = YumaLib.MOD_NAME, version = YumaLib.MOD_VERSION, useMetadata = true, guiFactory = YumaLibStat.MOD_CONFIG_GUI_FACTORY)
public class YumaLib {
	public static final String MOD_ID = "yumalib";
	public static final String MOD_NAME = "YumaLib";
	public static final String MOD_TEXTURE_DOMAIN = "yumalib";
	public static final String MOD_UNLOCALIZED_ENTRY_DOMAIN = "yumalib";
	public static final String MINECRAFT_VERSION = "1.7.10";
	public static final String MOD_VERSION = "0.0.0";
	public static final String MOD_VERSIONS_TSV_URL = "https://raw.githubusercontent.com/yuma140902/UpdateJSON_Forge/master/UpToDateModVersions.tsv";
	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
	
	@Mod.Metadata
	public static ModMetadata modMetadata;
	
	@Mod.Instance
	public static YumaLib INSTANCE;
	
	@SidedProxy(clientSide = YumaLibStat.PROXY_CLIENT, serverSide = YumaLibStat.PROXY_SERVER)
	public static CommonProxy proxy;
	
	public static SimpleNetworkWrapper networkWrapper;
	
	private UpdateChecker updateChecker;
	public UpdateChecker getUpdateChecker() {
		return updateChecker;
	}
	
	private void loadModMetadata(ModMetadata modMetadata) {
		modMetadata.modId = MOD_ID;
		modMetadata.name = MOD_NAME;
		modMetadata.version = MOD_VERSION;
		modMetadata.authorList.add("yuma140902");
		modMetadata.description = "Library for yuma140902's mods";
		modMetadata.url = "https://minecraft.curseforge.com/projects/yumalib";
		modMetadata.autogenerated = false;
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		loadModMetadata(modMetadata);
		ModConfigCore.loadConfig(event);
		
		updateChecker = new UpdateChecker(MOD_NAME, MOD_VERSIONS_TSV_URL, ModConfigCore.Stats.updateChannel(), MOD_VERSION, "https://www.google.com", ModConfigCore.Stats.isDebugMode());
		try {
			updateChecker.checkForUpdates();
		}
		catch (Exception e) {
			LOGGER.warn(e);
		}
		LOGGER.info(updateChecker.hasNewVersionAvailable() ? "There is a new version available. - v" + updateChecker.getAvailableNewVersion() + ". Visit " + updateChecker.getNewVersionUrl() : MOD_NAME + " is now up-to-date.");
		YumaLibApi.registerUpdateChecker(updateChecker);
		
		networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
		networkWrapper.registerMessage(NoteBlockPlayHandler.class, NoteBlockPlayMessage.class, 1, Side.CLIENT);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerEventHandlers();
	}
}
