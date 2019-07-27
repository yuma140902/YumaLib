package yuma140902.mcmods.yumalib.internal.proxy;

import java.util.Iterator;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.command.server.CommandMessageRaw;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import yuma140902.mcmods.yumalib.updatecheck.IUpdateChecker;
import yuma140902.mcmods.yumalib.updatecheck.UpdateNotifierRegistry;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	@Override
	public void registerEventHandlers() {
		super.registerEventHandlers();
		MinecraftForge.EVENT_BUS.register(UpdateNotifyingEventHandler.INSTANCE);
	}
	
	public static class UpdateNotifyingEventHandler {
		
		private UpdateNotifyingEventHandler() {}
		
		public static final UpdateNotifyingEventHandler INSTANCE = new UpdateNotifyingEventHandler();
		
		private boolean hasNotifiedAboutUpdate = false;
		
		@SubscribeEvent
		public void updateNotify(WorldEvent.Load event) {
			if(!event.world.isRemote || hasNotifiedAboutUpdate) {
				return;
			}
			IntegratedServer integratedServer = Minecraft.getMinecraft().getIntegratedServer();
			if(integratedServer == null) {
				return;
			}
			
			hasNotifiedAboutUpdate = true;
			
			Iterator<IUpdateChecker> iterator = UpdateNotifierRegistry.INSTANCE.iterator();
			while (iterator.hasNext()) {
				IUpdateChecker updateChecker = iterator.next();
				
				if(!updateChecker.hasNewVersionAvailable()) {
					continue;
				}
				String msgRaw = updateChecker.getNotificationMessageForChatGui();
				new CommandMessageRaw().processCommand(integratedServer, new String[] {"@a", msgRaw});
			}
		}
	}
	
}
