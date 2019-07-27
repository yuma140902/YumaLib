package yuma140902.mcmods.yumalib.internal.event_handlers;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import yuma140902.mcmods.yumalib.YumaLib;
import yuma140902.mcmods.yumalib.internal.config.ModConfigCore;

public class CommonEventHandler {
	private CommonEventHandler() {}
	
	public static final CommonEventHandler INSTANCE = new CommonEventHandler();
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if(YumaLib.MOD_ID.equals(event.modID))
			ModConfigCore.syncConfig();
	}
}
