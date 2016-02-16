package dao.impl;

import dao.BaseDao;
import dao.hibernate.HibernateUtil;
import models.Context;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseDaoImpl<T> implements BaseDao<T> {

    //TODO: implement more session methods e.g. saveOrUpdate

    @SuppressWarnings("unchecked")
    private Class<T> findClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void create(Context context, T entity) {
        HibernateUtil.withSession(context, session -> {
            session.save(entity);
        });
    }

    @Override
    public void update(Context context, T entity) {
        HibernateUtil.withSession(context, session -> {
            session.update(entity);
        });
    }

    @Override
    public void delete(Context context, T entity) {
        HibernateUtil.withSession(context, session -> {
            session.delete(entity);
        });
    }

    @Override
    public void deleteById(Context context, Integer id) {
        HibernateUtil.withSession(context, session -> {
            T entity = session.get(findClass(), id);
            session.delete(entity);
        });
    }

    @Override
    public T findById(Context context, int id) {
        return HibernateUtil.withSession(context, session -> {
            return session.get(findClass(), id);
        });
    }

    @Override
    public List<T> findAll(Context context) {
        return HibernateUtil.withSession(context, session -> {
            return (List<T>) session.createCriteria(findClass()).list();
        });
    }
}
