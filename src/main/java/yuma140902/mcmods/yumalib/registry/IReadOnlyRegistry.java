package yuma140902.mcmods.yumalib.registry;

import java.util.Iterator;

public interface IReadOnlyRegistry<T> {
	Iterator<T> iterator();
}
