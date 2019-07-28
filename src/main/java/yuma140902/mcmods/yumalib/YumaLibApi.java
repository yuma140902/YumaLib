package yuma140902.mcmods.yumalib;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import yuma140902.mcmods.yumalib.internal.fluid.FillBucketHandler;
import yuma140902.mcmods.yumalib.registry.GenericRegistry;
import yuma140902.mcmods.yumalib.registry.IReadOnlyIdRegistry;
import yuma140902.mcmods.yumalib.registry.IReadOnlyRegistry;
import yuma140902.mcmods.yumalib.registry.IdRegistry;
import yuma140902.mcmods.yumalib.sounds.INoteBlockInstrument;
import yuma140902.mcmods.yumalib.updatecheck.IUpdateChecker;

public class YumaLibApi {
	
	public static String getVersion() {
		return YumaLib.MOD_VERSION;
	}
	
	private static GenericRegistry<IUpdateChecker> updateNotifierRegistry = new GenericRegistry<>();
	
	/**
	 * Register UpdateCheckers here to use update notification on logging in to the world
	 * @param updateChecker
	 */
	public static void registerUpdateChecker(IUpdateChecker updateChecker) {
		updateNotifierRegistry.register(updateChecker);
	}
	
	public static IReadOnlyRegistry<IUpdateChecker> getUpdateNotifierRegistry(){
		return updateNotifierRegistry;
	}
	
	private static IdRegistry<INoteBlockInstrument> noteBlockInstrumentReg = new IdRegistry<>();
	
	public static IReadOnlyIdRegistry<INoteBlockInstrument> getNoteBlockInstrumentRegistry(){
		return noteBlockInstrumentReg;
	}
	
	public static void registerNoteBlockInstrument(INoteBlockInstrument noteBlockInstrument) {
		noteBlockInstrumentReg.register(noteBlockInstrument);
	}
	
	public static void registerCustomBucket(Block blockFluid, Item bucket) {
		FillBucketHandler.INSTANCE.registerCustomBucket(blockFluid, bucket);
	}
}
