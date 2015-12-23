package com.ovalsearch.dao.impl;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ovalsearch.dao.IEntityDao;
import com.ovalsearch.entity.BaseEntity;

@Named("entityDao")
public class EntityDaoImpl implements IEntityDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public <T extends BaseEntity> void saveOrUpdate(T object) {
        if (object.getId() != null) {
            entityManager.merge(object);
        } else {
            entityManager.persist(object);
        }
    }

    @Override
    public <T extends BaseEntity> void delete(T object) {
        entityManager.remove(object);
    }

    @Override
    public void flush() {
        entityManager.flush();
    }

    @Override
    public void clear() {
        entityManager.clear();
    }

    @Override
    public <T extends BaseEntity> T findById(Class<T> objectClass, Long id) {
        T object = null;
        object = entityManager.find(objectClass, id);
        return object;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseEntity> List<T> findAll(Class<T> objectClass) {
        Query query = entityManager.createQuery("Select data from " + objectClass.getName() + " data");
        List<T> objectList = (List<T>) query.getResultList();
        return objectList;
    }

    @Override
    public <T extends BaseEntity> Long getMaxId(Class<T> objectClass) {
        Query query = entityManager.createQuery("Select max(data.id) from " + objectClass.getName() + " data");
        Long id = (Long) query.getSingleResult();
        return id;
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseEntity> List<T> findAllEnabledObjects(Class<T> objectClass) {
        Query query = entityManager.createQuery("Select data from " + objectClass.getName() + " data where data.enabled = true");
        List<T> objectList = (List<T>) query.getResultList();
        return objectList;
    }

}
