package repository;

import domain.Identifiable;

import java.util.Iterator;

public interface InterfaceRepository<ID, T extends Identifiable<ID>> {
    void add(ID id, T object);

    void delete(ID id);

    Iterator<T> iterator();

    T findByID(ID id);
}
