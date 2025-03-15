package repository;

import domain.Identifiable;

import java.util.HashMap;
import java.util.Iterator;

public class MemoryRepository<ID, T extends Identifiable<ID>> implements InterfaceRepository<ID, T>{
    protected HashMap<ID, T> map = new HashMap<>();

    @Override
    public void add(ID id, T object) {
        map.put(id,object);
    }

    @Override
    public void delete(ID id) {
        map.remove(id);
    }

    @Override
    public Iterator<T> iterator() {
        return map.values().iterator();
    }

    @Override
    public T findByID(ID id) {
        return map.get(id);
    }
}
