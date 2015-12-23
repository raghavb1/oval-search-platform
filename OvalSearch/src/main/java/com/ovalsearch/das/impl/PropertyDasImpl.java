/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */  
package com.ovalsearch.das.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ovalsearch.dao.IEntityDao;
import com.ovalsearch.das.IPropertyDas;
import com.ovalsearch.entity.Property;

/**
 *  
 *  @version     1.0, 23-Dec-2015
 *  @author deepanshu
 */
@Service
public class PropertyDasImpl implements IPropertyDas{

    @Autowired
    private IEntityDao  entityDao;
    
    @Override
    public List<Property> getAllProperties() {
        return entityDao.findAll(Property.class);
    }

}
