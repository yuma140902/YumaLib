package yuma140902.mcmods.yumalib.updatecheck;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.command.server.CommandMessageRaw;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.world.WorldEvent;

/**
 * A forge event handler to notify update when player login the world.
 * Register its instance to Forge event bus by using MinecraftForge.EVENT_BUS.register()
 * @author yuma140902
 *
 */
public class UpdateNotifyingEventHandler {
	
	private final UpdateChecker updateChecker;
	
	public UpdateNotifyingEventHandler(UpdateChecker updateChecker) {
		this.updateChecker = updateChecker;
	}
	
	private boolean hasNotifiedAboutUpdate = false;
	
	@SubscribeEvent
	public void updateNotify(WorldEvent.Load event) {
			if(!event.world.isRemote || hasNotifiedAboutUpdate) {
				return;
			}
			if(!updateChecker.hasNewVersionAvailable()) {
				return;
			}
			IntegratedServer integratedServer = Minecraft.getMinecraft().getIntegratedServer();
			if(integratedServer == null) {
				return;
			}
			
			String msgRaw = StatCollector.translateToLocalFormatted("text.yumalib.update_notify", updateChecker.getModDisplayName(), updateChecker.getAvailableNewVersion(), updateChecker.getNewVersionUrl());
			new CommandMessageRaw().processCommand(integratedServer, new String[] {"@a", msgRaw});
			
			hasNotifiedAboutUpdate = true;
		}
}

