package yuma140902.mcmods.yumalib.fluid;

import javax.annotation.Nullable;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidHandler;

public interface IFluidTankContainer extends IFluidHandler {
	/**
	 * 利用可能なタンクのうち最初の一つを返します
	 */
	@Nullable
	FluidTank getMainTank();
}
