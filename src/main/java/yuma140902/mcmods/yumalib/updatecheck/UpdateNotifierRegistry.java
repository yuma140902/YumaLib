package yuma140902.mcmods.yumalib.updatecheck;

import yuma140902.mcmods.yumalib.registry.GenericRegistry;

/**
 * Register UpdateCheckers here to use update notification on logging in to the world
 * @author yuma140902
 *
 */
public class UpdateNotifierRegistry extends GenericRegistry<IUpdateChecker> {
	private UpdateNotifierRegistry() {}
	
	public static final UpdateNotifierRegistry INSTANCE = new UpdateNotifierRegistry();
}
