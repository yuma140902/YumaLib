package yuma140902.mcmods.yumalib.internal.event_handlers;

import java.util.Iterator;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
import yuma140902.mcmods.yumalib.YumaLib;
import yuma140902.mcmods.yumalib.YumaLibApi;
import yuma140902.mcmods.yumalib.internal.config.ModConfigCore;
import yuma140902.mcmods.yumalib.internal.fluid.FillBucketHandler;
import yuma140902.mcmods.yumalib.network.NoteBlockPlayMessage;
import yuma140902.mcmods.yumalib.sounds.INoteBlockInstrument;

public class CommonEventHandler {
	private CommonEventHandler() {}
	
	public static final CommonEventHandler INSTANCE = new CommonEventHandler();
	
	@SubscribeEvent
	public void onFillBucket(FillBucketEvent event) {
		FillBucketHandler.INSTANCE.onFillBucket(event);
	}
	
	@SubscribeEvent
	public void onNoteBlockPlay(NoteBlockEvent.Play event) {
		
		World world = event.world;
		int x = event.x;
		int y = event.y;
		int z = event.z;
		int dimId = world.provider.dimensionId;
		int noteId = event.getVanillaNoteId();
		INoteBlockInstrument instrument = null;
		
		boolean matched = false;
		Iterator<INoteBlockInstrument> iterator = YumaLibApi.getNoteBlockInstrumentRegistry().iterator();
		while (iterator.hasNext()) {
			instrument = iterator.next();
			if(instrument.matches(world, x, y, z)) {
				matched = true;
				break;
			}
		}
		
		if(!matched) {
			return;
		}
		
		YumaLib.networkWrapper.sendToAllAround(new NoteBlockPlayMessage(instrument, noteId, dimId, x, y, z), new TargetPoint(dimId, x, y, z, 32));
		
		event.setCanceled(true);
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if(YumaLib.MOD_ID.equals(event.modID))
			ModConfigCore.syncConfig();
	}
}
