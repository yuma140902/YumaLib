package yuma140902.mcmods.yumalib.fluid;

import java.util.EnumSet;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FillFluidContainerHandler {
	private FillFluidContainerHandler() {}
	
	public enum Operation {
		SHOW,
		FILL,
		DRAIN
	}
	
	/**
	 * 液体コンテナアイテムと内部にタンクを持つブロックとのやり取りの処理。
	 * 満たされた液体コンテナを持っていればタンクへの搬入を試み、
	 * 空の液体コンテナを持っていればタンクからの搬出を試みる。
	 * 素手ならタンクの内容をチャットに表示する。
	 * {@link net.minecraft.block.Block#onBlockActivated(World, int, int, int, EntityPlayer, int, float, float, float)}
	 * から呼び出されることを想定してる
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param player
	 * @return 何かしらの処理(液体の搬入、搬出、内容量の表示)が行われたかどうか。
	 * {@link net.minecraft.block.Block#onBlockActivated(World, int, int, int, EntityPlayer, int, float, float, float)}
	 * の戻り値とは違うので注意
	 */
	@SuppressWarnings("null")
	public static boolean fillFluidContainerOnBlockActivated(@Nonnull World world, int x, int y, int z, @Nonnull EntityPlayer player) {
		return fillFluidContainerOnBlockActivated(EnumSet.of(Operation.SHOW, Operation.FILL, Operation.DRAIN), world, x, y, z, player);
	}
	
	/**
	 * 液体コンテナアイテムと内部にタンクを持つブロックとのやり取りの処理。
	 * 満たされた液体コンテナを持っていればタンクへの搬入を試み、
	 * 空の液体コンテナを持っていればタンクからの搬出を試みる。
	 * 素手ならタンクの内容をチャットに表示する。
	 * {@link net.minecraft.block.Block#onBlockActivated(World, int, int, int, EntityPlayer, int, float, float, float)}
	 * から呼び出されることを想定してる
	 * @param operations
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param player
	 * @return 何かしらの処理(液体の搬入、搬出、内容量の表示)が行われたかどうか。
	 * {@link net.minecraft.block.Block#onBlockActivated(World, int, int, int, EntityPlayer, int, float, float, float)}
	 * の戻り値とは違うので注意
	 */
	public static boolean fillFluidContainerOnBlockActivated(@Nonnull EnumSet<Operation> operations, @Nonnull World world, int x, int y, int z, @Nonnull EntityPlayer player) {
		/* 手持ちアイテム */
		ItemStack itemstackHeld = player.getHeldItem();
		/* このブロックのTileEntity */
		TileEntity tileentity = world.getTileEntity(x, y, z);
		
		if(tileentity == null || !(tileentity instanceof IFluidTankContainer)) {
			return false;
		}
		
		IFluidTankContainer tankContainer = (IFluidTankContainer) tileentity;
		
		// TileEntityの液体タンクに入っている液体を取得
		FluidTank mainTank = tankContainer.getMainTank();
		if(mainTank == null) {
			player.addChatMessage(new ChatComponentText("No tank"));
			return false;
		}
		FluidStack fluid = mainTank.getFluid();
		
		if (itemstackHeld == null)// 素手
		{
			if(!operations.contains(Operation.SHOW) || !player.isSneaking()) return false;
			
			String msg;
			
			if (fluid != null && fluid.getFluid() != null) {
				msg = "Fluid: " + StatCollector.translateToLocal(fluid.getFluid().getUnlocalizedName(fluid)) + " " + fluid.amount + "mB";
			}
			else {
				msg = "No fluid in the tank";
			}
			
			if (!world.isRemote)
				player.addChatMessage(new ChatComponentText(msg));
			
			return true;
		}
		else {
			// このメソッドにより、手持ちのアイテムが液体容器に登録されたアイテムかどうか、及び入っている液体を取得する。
			FluidStack fluidHeld = FluidContainerRegistry.getFluidForFilledItem(itemstackHeld);
			
			// 満たされた液体コンテナを持っている場合
			if (fluidHeld != null && fluidHeld.getFluid() != null) {
				
				if(!operations.contains(Operation.FILL)) {
					return false;
				}
				
				/*
				 * fillメソッドの第二引数にfalseを入れた場合、実際に液体をタンクに入れるのではなく、
				 * タンクに投入可能な液体の量をシュミレートして値を返す。
				 */
				int put = tankContainer.fill(ForgeDirection.UNKNOWN, fluidHeld, false);
				
				// 全量投入可能なときのみ
				if (put == fluidHeld.amount) {
					// 今度は液体を液体タンクに入れるので、第二引数はtrueにする。
					tankContainer.fill(ForgeDirection.UNKNOWN, fluidHeld, true);
					
					// 液体容器を空にして、空容器を得る。
					ItemStack emptyContainer = FluidContainerRegistry.drainFluidContainer(itemstackHeld);
					if (emptyContainer != null && !player.capabilities.isCreativeMode) {
						if (!player.inventory.addItemStackToInventory(emptyContainer.copy())) {
							player.entityDropItem(emptyContainer.copy(), 1);
						}
					}
					
					// プレイヤーの手持ちアイテムを減らす処理
					if (!player.capabilities.isCreativeMode && itemstackHeld.stackSize-- <= 0) {
						player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
					}
					
					// 更新を伝える処理
					// TileEntityを更新した場合、このように更新処理を挟まないと見た目に反映しない。
					tileentity.markDirty();
					player.inventory.markDirty();
					world.markBlockForUpdate(x, y, z);
					
					// 効果音の発生
					world.playSoundAtEntity(player, "random.pop", 0.4F, 1.8F);
					
					return true;
				}
			}
			else {
				// 液体タンクに何かしら入っている時
				if (fluid != null && fluid.getFluid() != null) {
					
					if(!operations.contains(Operation.DRAIN)) {
						return false;
					}
					
					ItemStack filledContainer = FluidContainerRegistry.fillFluidContainer(fluid, itemstackHeld);
					
					int amountToDrain = FluidContainerRegistry.getContainerCapacity(filledContainer);
					if (amountToDrain == 0 || fluid.amount < amountToDrain) //持っているアイテムが空の液体コンテナではない、あるいは液体コンテナを満たすのに十分な量の液体がタンクの中にないとき
						return false;
					
					if (filledContainer != null) {
						/*
						 * タンクの液体の減少処理
						 * タンク容量 > amountToDrain であることを事前にチェック済みのためにここではシュミレート無しとしたが、
						 * fillの場合と同様に、シュミレートで投入可能量を確かめでから行っても良いと思う。
						 */
						
						tankContainer.drain(ForgeDirection.UNKNOWN, amountToDrain, true);
						
						// プレイヤーに、先に取得した「液体で満たされた容器アイテム」を与える処理
						if(!player.capabilities.isCreativeMode) {
							if (!player.inventory.addItemStackToInventory(filledContainer.copy())) {
								player.entityDropItem(filledContainer.copy(), 1);
							}
						}
						
						// プレイヤーの手持ちアイテムを減らす処理
						if (!player.capabilities.isCreativeMode && itemstackHeld.stackSize-- <= 0) {
							player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
						}
						
						// 更新を伝える処理
						// TileEntityを更新した場合、このように更新処理を挟まないと見た目に反映しない。
						tileentity.markDirty();
						player.inventory.markDirty();
						world.markBlockForUpdate(x, y, z);
						
						// 効果音の発生
						world.playSoundAtEntity(player, "random.pop", 0.4F, 1.8F);
					}
					
					return true;
				}
				else {
					// アイテムが液体入り容器でなく、かつタンクが空だった場合は何もしない
					return false;
				}
			}
		}
		
		return false;
	}
}
