/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ovalsearch.services.IStartupService;

/**
 * @version 1.0, 22-Dec-2015
 * @author deepanshu
 */
public class OvalsearchContextListener implements ServletContextListener {

    private static final Logger LOG = LoggerFactory.getLogger(OvalsearchContextListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        LOG.info("Context Destroyed");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("Context Initialization event called");
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        final IStartupService startupService = context.getBean(IStartupService.class);
        try {
            LOG.info("Loading Application Context");
            startupService.loadContext();
        } catch (Exception e) {
            LOG.error("Error while initializing application:", e);
        }
    }

}
