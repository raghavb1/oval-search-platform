/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.utils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ovalsearch.annotation.Cache;

/**
 * @version 1.0, 21-Dec-2015
 * @author deepanshu
 */
public class CacheManager {

    private static final Logger LOG       = LoggerFactory.getLogger(CacheManager.class);
    private static CacheManager _instance = new CacheManager();
    private Map<String, Object> _caches   = new ConcurrentHashMap<String, Object>();

    public static CacheManager getInstance() {
        return _instance;
    }

    public Object getCache(String cacheName) {
        Object cache = _caches.get(cacheName);
        return cache;
    }

    public Object getCacheByName(String cacheName) {
        return getCache(cacheName);
    }

    @SuppressWarnings("unchecked")
    public <T> T getCache(Class<T> cacheClass) {
        if (cacheClass.isAnnotationPresent(Cache.class)) {
            return (T) getCache(cacheClass.getAnnotation(Cache.class).name());
        } else {
            throw new IllegalArgumentException("@Cache annotation should be present for cache class:" + cacheClass.getName());
        }
    }

    public synchronized void setCache(Object cache) {
        Class<? extends Object> cacheClass = cache.getClass();
        if (cacheClass.isAnnotationPresent(Cache.class)) {
            for (Method m : cacheClass.getDeclaredMethods()) {
                if ("freeze".equals(m.getName())) {
                    try {
                        m.invoke(cache);
                    } catch (Exception e) {
                        LOG.error("unable to freeze cache:" + cacheClass.getName(), e);
                    }
                }
            }
            Cache annotation = cacheClass.getAnnotation(Cache.class);
            _caches.put(annotation.name(), cache);
        } else {
            throw new IllegalArgumentException("@Cache annotation should be present for cache class:" + cache.getClass().getName());
        }

    }
}
