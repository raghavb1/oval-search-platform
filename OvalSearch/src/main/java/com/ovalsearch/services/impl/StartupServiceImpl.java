/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ovalsearch.cache.ApplicationsCache;
import com.ovalsearch.cache.PropertyMapCache;
import com.ovalsearch.das.IApplicationsDas;
import com.ovalsearch.das.IPropertyDas;
import com.ovalsearch.entity.Applications;
import com.ovalsearch.enums.Property;
import com.ovalsearch.scheduler.TaskScheduler;
import com.ovalsearch.services.IStartupService;
import com.ovalsearch.threads.DownloadDataThread;
import com.ovalsearch.utils.CacheManager;

/**
 * @version 1.0, 21-Dec-2015
 * @author deepanshu
 */
@Service
public class StartupServiceImpl implements IStartupService {

    @Autowired
    private TaskScheduler       taskScheduler;

    @Autowired
    private IApplicationsDas    applicationsDas;

    @Autowired
    private IPropertyDas        propertyDas;

    private static final Logger LOG = LoggerFactory.getLogger(StartupServiceImpl.class);

    @Override
    public void loadContext() {
        loadProperties();
        loadApplicationsCache();
        loadTaskScheduler();
    }

    @Override
    public void loadTaskScheduler() {
        if (taskScheduler.getExecutorService() == null) {
            LOG.info("Creating a new Executor service with fixed thread pool");
            loadNewExecutorServiceAndScheduleTasks();
        } else {
            LOG.info("Executor service already exists. Shutting it down and creating a new one");
            taskScheduler.getScheduledTask().cancel(true);
            taskScheduler.getExecutorService().shutdown();
            loadNewExecutorServiceAndScheduleTasks();
        }
    }

    @Override
    public void loadProperties() {
        LOG.info("Loading properties from Enum");
        PropertyMapCache cache = new PropertyMapCache();
        for (Property p : Property.values()) {
            cache.addProperty(p.getName(), p.getValue());
        }
        LOG.info("Loaded properties from enum. Getting properties from DB");
        List<com.ovalsearch.entity.Property> dbProperties = propertyDas.getAllProperties();
        if (dbProperties != null && dbProperties.size() > 0) {
            for (com.ovalsearch.entity.Property property : dbProperties) {
                cache.addProperty(property.getName(), property.getValue());
            }
        }
        LOG.info("Loaded properties from DB");
        CacheManager.getInstance().setCache(cache);
        LOG.info("Loaded properties.");
    }

    private int getInitialDelayInMinutes() {
        int initialDelay = 0;
        try {
            String scheduleTime = CacheManager.getInstance().getCache(PropertyMapCache.class).getPropertyString(Property.FILE_DOWNLOAD_TIME);
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date scheduledDate = dateFormat.parse(scheduleTime);
            Date currentDate = new Date();
            Calendar scheduledCalendarInstance = Calendar.getInstance();
            scheduledCalendarInstance.setTime(scheduledDate);
            Calendar currentCalendarInstance = Calendar.getInstance();
            currentCalendarInstance.setTime(currentDate);
            int minutesToCompleteHour = 60 - currentCalendarInstance.get(Calendar.MINUTE);
            currentCalendarInstance.add(Calendar.MINUTE, minutesToCompleteHour);
            int scheduleHourUnit = scheduledCalendarInstance.get(Calendar.HOUR_OF_DAY);
            int currentHourUnit = currentCalendarInstance.get(Calendar.HOUR_OF_DAY);
            initialDelay = scheduleHourUnit >= currentHourUnit ? scheduleHourUnit - currentHourUnit : currentHourUnit - scheduleHourUnit;
            initialDelay = initialDelay * 60 + minutesToCompleteHour;
        } catch (ParseException e) {
            LOG.error("Exception in parsing date", e);
        }
        return initialDelay;
    }

    @Override
    public void loadApplicationsCache() {
        LOG.info("Loading applications cache from DB");
        ApplicationsCache cache = new ApplicationsCache();
        List<Applications> applications = applicationsDas.getAllApplications();
        if (applications != null && applications.size() > 0) {
            for (Applications application : applications) {
                cache.addApplication(application);
            }
        }
        CacheManager.getInstance().setCache(cache);
        LOG.info("Loaded applications cache from DB");

    }

    private void loadNewExecutorServiceAndScheduleTasks() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(CacheManager.getInstance().getCache(PropertyMapCache.class).getPropertyInteger(
                Property.SCHEDULER_THREAD_POOL_SIZE));
        taskScheduler.setExecutorService(executorService);
        LOG.info("Scheduling Task in newly created Executor Service");
        ScheduledFuture<?> futureObject = taskScheduler.getExecutorService().scheduleAtFixedRate(new DownloadDataThread(), getInitialDelayInMinutes(),
                CacheManager.getInstance().getCache(PropertyMapCache.class).getPropertyInteger(Property.FILE_DOWNLOAD_PERIOD) * 60, TimeUnit.MINUTES);
        taskScheduler.setScheduledTask(futureObject);
    }
}
