package yuma140902.mcmods.yumalib.registry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GenericRegistry<T> implements IReadOnlyRegistry<T> {
	
	protected List<T> list = new ArrayList<>();
	
	public void register(T item) {
		list.add(item);
	}
	
	public Iterator<T> iterator(){
		return list.iterator();
	}
}
