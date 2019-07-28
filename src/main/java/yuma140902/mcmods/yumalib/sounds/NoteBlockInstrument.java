package yuma140902.mcmods.yumalib.sounds;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class NoteBlockInstrument implements INoteBlockInstrument {
	
	private final String soundName;
	private final Block block;
	
	/**
	 * 
	 * @param soundName  (domained) sound name. example: "uptodate:note.guitar"
	 * @param block
	 */
	public NoteBlockInstrument(String soundName, Block block) {
		this.soundName = soundName;
		this.block = block;
	}
	
	@Override
	public String getSoundName() {
		return soundName;
	}
	
	public boolean matches(Block block) {
		return (block == null) ? false : (this.block == block);
	}
	
	@Override
	public boolean matches(World world, int x, int y, int z) {
		Block blockUnder = world.getBlock(x, y-1, z);
		return matches(blockUnder);
	}
  
}
