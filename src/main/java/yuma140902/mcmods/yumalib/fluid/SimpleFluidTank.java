package yuma140902.mcmods.yumalib.fluid;

import javax.annotation.Nullable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class SimpleFluidTank extends FluidTank {

	public SimpleFluidTank(int capacity) {
		super(capacity);
	}
	
	public SimpleFluidTank(FluidStack stack, int capacity) {
		super(stack, capacity);
	}
	
	public SimpleFluidTank(Fluid fluid, int amount, int capacity) {
		super(fluid, amount, capacity);
	}
	
	public boolean isEmpty() {
		return getFluid() == null || getFluid().getFluid() == null || getFluid().amount <= 0;
	}
	
	public boolean isFull() {
		return getFluid() != null && getFluid().amount == getCapacity();
	}
	
	@Nullable
	public Fluid getFluidType() {
		return getFluid() != null ? getFluid().getFluid() : null;
	}
	
	@SuppressWarnings("null")
	public String getFluidName() {
		return getFluid() != null && fluid.getFluid() != null ? getFluidType().getUnlocalizedName(this.fluid) : "Empty";
	}
	
	@SideOnly(Side.CLIENT)
	public void setAmount(int amount) {
		if(this.fluid != null && this.fluid.getFluid() != null) {
			this.fluid.amount = amount;
		}
	}
}
