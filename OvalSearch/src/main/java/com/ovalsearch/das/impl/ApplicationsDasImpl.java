/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.das.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ovalsearch.dao.IApplicationsDao;
import com.ovalsearch.das.IApplicationsDas;
import com.ovalsearch.entity.Applications;

/**
 * @version 1.0, 23-Dec-2015
 * @author deepanshu
 */
@Service
public class ApplicationsDasImpl implements IApplicationsDas {

    @Autowired
    private IApplicationsDao    applicationsDao;

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationsDasImpl.class);

    @Override
    public List<Applications> getAllApplications() {
        LOG.info("Getting all applications detail from Dao");
        return applicationsDao.getAllApplications();
    }

    @Override
    public Applications getApplicationByApkId(String apkId) {
        LOG.info("Getting application from Dao for apkId " + apkId);
        return applicationsDao.getApplicationByApkId(apkId);
    }

}
