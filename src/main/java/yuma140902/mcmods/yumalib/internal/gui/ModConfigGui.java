package yuma140902.mcmods.yumalib.internal.gui;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import yuma140902.mcmods.yumalib.YumaLib;
import yuma140902.mcmods.yumalib.internal.config.ModConfigCore;

public class ModConfigGui extends GuiConfig {
	public ModConfigGui(GuiScreen parent) {
		super(parent, (new ConfigElement<Object>(ModConfigCore.cfg.getCategory(ModConfigCore.CATEGORY_GENERAL))).getChildElements(), YumaLib.MOD_ID, false, false, YumaLib.MOD_NAME);
	}
}
