/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.services;

/**
 * @version 1.0, 21-Dec-2015
 * @author deepanshu
 */
public interface IStartupService {

    void loadContext();
    void loadTaskScheduler();
    void loadProperties();
    void loadApplicationsCache();
}
