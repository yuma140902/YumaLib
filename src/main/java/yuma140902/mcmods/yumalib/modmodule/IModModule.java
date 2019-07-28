package yuma140902.mcmods.yumalib.modmodule;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public interface IModModule<T> {
	void preInit(FMLPreInitializationEvent event);
	void init(FMLInitializationEvent event);
	void postInit(FMLPostInitializationEvent event);
	
	T getParentMod();
	String getModuleDisplayName();
	boolean isEnabled();
}
