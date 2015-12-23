/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.scheduler;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.springframework.stereotype.Component;

/**
 * @version 1.0, 21-Dec-2015
 * @author deepanshu
 */
@Component("taskScheduler")
public class TaskScheduler {

    private ScheduledExecutorService executorService;

    private ScheduledFuture<?>       scheduledTask;

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ScheduledExecutorService executorService) {
        this.executorService = executorService;
    }

    public ScheduledFuture<?> getScheduledTask() {
        return scheduledTask;
    }

    public void setScheduledTask(ScheduledFuture<?> scheduledTask) {
        this.scheduledTask = scheduledTask;
    }

}
