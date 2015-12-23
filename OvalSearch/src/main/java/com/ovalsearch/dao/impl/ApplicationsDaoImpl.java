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

import com.ovalsearch.dao.IApplicationsDao;
import com.ovalsearch.dao.IEntityDao;
import com.ovalsearch.entity.Applications;

/**
 *  
 *  @version     1.0, 22-Dec-2015
 *  @author deepanshu
 */
@Service
public class ApplicationsDaoImpl implements IApplicationsDao{

    @Autowired
    private IEntityDao entityDao;
    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Applications> getAllApplications() {
        Query query = entityDao.getEntityManager().createQuery("Select applications from Applications applications order by downloads,rating");
        List<Applications> resultSet = (List<Applications>) query.getResultList();
        return resultSet;
    }

}