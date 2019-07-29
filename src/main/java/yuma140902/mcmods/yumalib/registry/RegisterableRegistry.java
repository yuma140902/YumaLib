package yuma140902.mcmods.yumalib.registry;

import java.util.Iterator;

public class RegisterableRegistry<T> extends GenericRegistry<T> implements IRegisterable {
	
	@Override
	public void register() {
		Iterator<T> iterator = iterator();
		while (iterator.hasNext()) {
			T item = iterator.next();
			if(item instanceof IRegisterable) {
				((IRegisterable) item).register();
			}
		}
	}
}
