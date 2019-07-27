package yuma140902.mcmods.yumalib.client.gui;

import net.minecraft.util.ResourceLocation;

public interface IResourceLocationPart {
	public int getU();
	public int getV();
	public int getWidth();
	public int getHeight();
	public ResourceLocation getResourceLocation();
}
