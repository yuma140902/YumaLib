package yuma140902.mcmods.yumalib.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import yuma140902.mcmods.yumalib.YumaLibApi;
import yuma140902.mcmods.yumalib.sounds.INoteBlockInstrument;

public class NoteBlockPlayMessage implements IMessage {
	
	public INoteBlockInstrument instrument;
	public int noteId;
	public int dimId, x, y, z;
	
	public NoteBlockPlayMessage() {}
	
	public NoteBlockPlayMessage(INoteBlockInstrument instrument, int noteId, int dimId, int x, int y, int z) {
		this.instrument = instrument;
		this.noteId = noteId;
		this.dimId = dimId;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		dimId = buf.readInt();
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		instrument = YumaLibApi.getNoteBlockInstrumentRegistry().getFromId(buf.readInt());
		noteId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(dimId);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(YumaLibApi.getNoteBlockInstrumentRegistry().getId(instrument));
		buf.writeInt(noteId);
	}
	
}
