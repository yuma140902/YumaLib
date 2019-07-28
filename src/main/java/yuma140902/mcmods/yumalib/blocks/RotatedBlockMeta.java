package yuma140902.mcmods.yumalib.blocks;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class RotatedBlockMeta {
	
	private RotatedBlockMeta() {}
	
	public static final int META_NORTH = 2, META_EAST = 3, META_SOUTH = 0, META_WEST = 1;
	
	public static int getRotationMetaFromEntity(EntityLivingBase entity) {
		int rotation = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		return rotation;
	}
}
