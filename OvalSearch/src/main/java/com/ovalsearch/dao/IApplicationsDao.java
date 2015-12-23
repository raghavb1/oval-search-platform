/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */  
package com.ovalsearch.dao;

import java.util.List;

import com.ovalsearch.entity.Applications;

/**
 *  
 *  @version     1.0, 22-Dec-2015
 *  @author deepanshu
 */
public interface IApplicationsDao {

    public List<Applications> getAllApplications();
}
