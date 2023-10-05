package app.repositories;

import java.util.List;

public interface ScootersRepository<E> {
    List<E> findAll();           // finds all available instances
}
