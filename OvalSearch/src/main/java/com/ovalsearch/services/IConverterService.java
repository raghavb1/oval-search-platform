/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.services;

import java.util.List;

import com.ovalsearch.base.ApplicationsSro;
import com.ovalsearch.entity.Applications;

/**
 * @version 1.0, 23-Dec-2015
 * @author deepanshu
 */
public interface IConverterService {

    public List<ApplicationsSro> getSroFromEntity(List<Applications> applications);

	ApplicationsSro getApplicationSroFromEntity(Applications application);
}
