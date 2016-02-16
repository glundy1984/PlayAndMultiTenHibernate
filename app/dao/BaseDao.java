package dao;

import models.Context;

import java.util.List;

public interface BaseDao<T> {

    void create(Context context, T entity);

    void update(Context context, T entity);

    void delete(Context context, T entity);

    void deleteById(Context context, Integer id);

    T findById(Context context, int id);

    List<T> findAll(Context context);

}
