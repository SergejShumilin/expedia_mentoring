package com.epam.esm.dao;

import java.util.List;

public interface TagDao<T> extends Dao<T> {
    T findByName(String name);
}
