package yuma140902.mcmods.yumalib.registry;

import java.util.Iterator;

public class RegisterableRegistry<T extends IRegisterable> extends GenericRegistry<T> implements IRegisterable {
	
	@Override
	public void register() {
		Iterator<T> iterator = iterator();
		while (iterator.hasNext()) {
			IRegisterable registerable = iterator.next();
			registerable.register();
		}
	}
}
