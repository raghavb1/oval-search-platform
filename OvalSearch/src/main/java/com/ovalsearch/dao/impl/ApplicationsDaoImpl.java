/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ovalsearch.cache.PropertyMapCache;
import com.ovalsearch.dao.IApplicationsDao;
import com.ovalsearch.dao.IEntityDao;
import com.ovalsearch.entity.Applications;
import com.ovalsearch.enums.Property;
import com.ovalsearch.utils.CacheManager;

/**
 * @version 1.0, 22-Dec-2015
 * @author deepanshu
 */
@Service
public class ApplicationsDaoImpl implements IApplicationsDao {

    @Autowired
    private IEntityDao entityDao;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Applications> getAllApplications() {
        Query query = entityDao.getEntityManager().createQuery("Select applications from Applications applications order by downloads DESC, rating DESC");
        List<Applications> resultSet = (List<Applications>) query.getResultList();
        return resultSet;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public Applications getApplicationByApkId(String apkId) {
        Query query = entityDao.getEntityManager().createQuery("Select applications from Applications applications where apkId =:apkId");
        query.setParameter("apkId", apkId);
        List<Applications> applications = (List<Applications>) query.getResultList();
        if (applications != null && applications.size() > 0) {
            return applications.get(0);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Applications> getAllApplicationsByQuery() {
        String queryString = CacheManager.getInstance().getCache(PropertyMapCache.class).getPropertyString(Property.ALL_APPLICATIONS_QUERY);
        Query query = entityDao.getEntityManager().createQuery(queryString);
        List<Applications> resultSet = (List<Applications>) query.getResultList();
        return resultSet;
    }

}
