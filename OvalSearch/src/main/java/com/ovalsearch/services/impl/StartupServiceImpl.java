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
import com.ovalsearch.dao.IEntityDao;
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

    @Autowired
    private IEntityDao          entityDao;

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
        int delayInMinutes = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            Date passedDate = df.parse(CacheManager.getInstance().getCache(PropertyMapCache.class).getPropertyString(Property.FILE_DOWNLOAD_TIME));
            Date currentDate = new Date();
            Calendar passedDateCalendar = Calendar.getInstance();
            Calendar currentDateCalendar = Calendar.getInstance();
            passedDateCalendar.setTime(passedDate);
            currentDateCalendar.setTime(currentDate);
            int currentDateHour = currentDateCalendar.get(Calendar.HOUR_OF_DAY);
            int passedDateHour = passedDateCalendar.get(Calendar.HOUR_OF_DAY);
            int currentDateMinutes = currentDateCalendar.get(Calendar.MINUTE);
            int passedDateMinutes = passedDateCalendar.get(Calendar.MINUTE);
            int minutesPassed12AMforPassedDate = passedDateHour * 60 + passedDateMinutes;
            int minutesPassed12AMforCurrentDate = currentDateHour * 60 + currentDateMinutes;
            delayInMinutes = minutesPassed12AMforPassedDate >= minutesPassed12AMforCurrentDate ? minutesPassed12AMforPassedDate - minutesPassed12AMforCurrentDate
                    : 24 * 60 - (minutesPassed12AMforCurrentDate - minutesPassed12AMforPassedDate);
        } catch (ParseException e) {
            LOG.error("Error occurred while reading initial time for Close Hos Task. Error message {} ", e);
        }
        LOG.info("Initial delay for downloading : " + delayInMinutes);
        return delayInMinutes;
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
        ScheduledFuture<?> futureObject = taskScheduler.getExecutorService().scheduleAtFixedRate(new DownloadDataThread(entityDao), getInitialDelayInMinutes(),
                CacheManager.getInstance().getCache(PropertyMapCache.class).getPropertyInteger(Property.FILE_DOWNLOAD_PERIOD) * 60, TimeUnit.MINUTES);
        taskScheduler.setScheduledTask(futureObject);
    }
}
