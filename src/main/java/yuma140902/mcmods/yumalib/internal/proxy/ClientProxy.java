package yuma140902.mcmods.yumalib.internal.proxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.MinecraftForge;
import yuma140902.mcmods.yumalib.YumaLib;
import yuma140902.mcmods.yumalib.updatecheck.UpdateNotifyingEventHandler;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	@Override
	public void registerEventHandlers() {
		super.registerEventHandlers();
		MinecraftForge.EVENT_BUS.register(new UpdateNotifyingEventHandler(YumaLib.INSTANCE.getUpdateChecker()));
	}
	
}
