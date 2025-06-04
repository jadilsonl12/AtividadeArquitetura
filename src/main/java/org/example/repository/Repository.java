package org.example.repository;

import java.util.List;

public interface Repository<T> {
    void save(T item);
    List<T> findAll();
    T findById(int id);
    void deleteById(int id);
}

