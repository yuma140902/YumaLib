package yuma140902.mcmods.yumalib.registry;

public class IdRegistry<T> extends GenericRegistry<T> implements IReadOnlyIdRegistry<T> {
	
	public int getId(T value) {
		return list.indexOf(value);
	}
	
	public T getFromId(int id) {
		return list.get(id);
	}
}
