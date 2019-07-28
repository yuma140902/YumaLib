package yuma140902.mcmods.yumalib.registry;

import java.util.Iterator;

public interface IReadOnlyIdRegistry<T> extends IReadOnlyRegistry<T> {
	int getId(T value);
	T getFromId(int id);
	Iterator<T> iterator();
}
