package com.epam.esm.dao;

import java.util.List;

public interface Dao<T> {
    void save(T t);
    void delete(int id);
    List<T> findAll();
    T findById(int id);
}
