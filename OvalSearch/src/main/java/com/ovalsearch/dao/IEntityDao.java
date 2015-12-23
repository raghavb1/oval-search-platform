package com.ovalsearch.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.ovalsearch.entity.BaseEntity;

@Repository
public interface IEntityDao {
    public <T extends BaseEntity> void saveOrUpdate(T object);

    public <T extends BaseEntity> void delete(T object);

    public <T extends BaseEntity> T findById(Class<T> objectClass, Long id);

    public <T extends BaseEntity> List<T> findAll(Class<T> objectClass);

    public <T extends BaseEntity> List<T> findAllEnabledObjects(Class<T> objectClass);

    public <T extends BaseEntity> Long getMaxId(Class<T> objectClass);

    public void flush();

    public void clear();

    public EntityManager getEntityManager();
}
