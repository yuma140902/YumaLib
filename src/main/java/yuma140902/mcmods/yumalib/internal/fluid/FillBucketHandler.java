package yuma140902.mcmods.yumalib.internal.fluid;

import java.util.HashMap;
import java.util.Map;
import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public final class FillBucketHandler {
	private FillBucketHandler() {}
	
	public static final FillBucketHandler INSTANCE = new FillBucketHandler();
	
	private Map<Block, Item> buckets = new HashMap<Block, Item>();
	
	public void registerCustomBucket(Block blockFluid, Item bucket) {
		buckets.put(blockFluid, bucket);
	}
	
	public void onFillBucket(FillBucketEvent event) {
		ItemStack fluidContainer = fillCustomBucket(event.world, event.target);
		
		if(fluidContainer == null) return;
		
		event.result = fluidContainer;
		event.setResult(Result.ALLOW);
	}
	
	/**
	 * 
	 * @param world
	 * @param pos
	 * @return 満たされたバケツアイテム。対応するものがなければnull
	 */
	private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
		Block fluid = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
		Item bucket = buckets.get(fluid);

		if(bucket != null && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
			world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
			return new ItemStack(bucket);
		}
		
		return null;
	}
}
