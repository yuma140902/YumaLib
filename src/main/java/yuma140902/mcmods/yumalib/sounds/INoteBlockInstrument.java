package yuma140902.mcmods.yumalib.sounds;

import net.minecraft.world.World;

public interface INoteBlockInstrument {
	/**
	 * @return (domained) sound name. example: "uptodate:note.guitar"
	 */
	String getSoundName();
	boolean matches(World world, int x, int y, int z);
}
