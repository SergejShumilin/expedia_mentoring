package com.epam.esm.dao;

public interface GiftCertificatesDao<T> extends Dao<T> {
    void update(T t);
}
