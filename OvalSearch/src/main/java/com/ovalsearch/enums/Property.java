/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.enums;

/**
 * @version 1.0, 21-Dec-2015
 * @author deepanshu
 */
public enum Property {

    FILE_DOWNLOAD_TIME("file.download.time", "05:00"), SCHEDULER_THREAD_POOL_SIZE("scheduler.thread.pool.size", "1"), CACHE_RELOAD_PASSWORD("cache.reload.password", "ovalsearch"), FILE_DOWNLOAD_PERIOD(
            "file.download.period", "24"), FILENAME("filename", "information.xml"), REMOTE_REPO("remote.repo", "http://apps.store.aptoide.com/info.xml");

    private String name;

    private String value;

    private Property(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}
