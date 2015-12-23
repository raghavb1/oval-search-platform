/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ovalsearch.cache.PropertyMapCache;
import com.ovalsearch.enums.Property;
import com.ovalsearch.services.IStartupService;
import com.ovalsearch.utils.CacheManager;

/**
 * @version 1.0, 23-Dec-2015
 * @author deepanshu
 */

@Controller
@RequestMapping("/reloadCache")
public class CacheReloadController {

    @Autowired
    private IStartupService     startupService;

    private static final Logger LOG = LoggerFactory.getLogger(CacheReloadController.class);

    @RequestMapping("")
    @ResponseBody
    public String reloadCache(@RequestParam(required = false, value = "pwd") String password) {
        String message = null;
        if (password != null && password.equalsIgnoreCase(CacheManager.getInstance().getCache(PropertyMapCache.class).getPropertyString(Property.CACHE_RELOAD_PASSWORD))) {
            LOG.info("Request received to reload cache");
            try {
                startupService.loadContext();
                LOG.info("Cache Reloaded Successfully");
                message = "Cache reloaded Successfully";
            } catch (Exception e) {
                LOG.error("Error while reloading cache", e);
                message = "Error while reloading Cache. Please check logs";
            }
        } else {
            message = "Failed to reload Cache. Password Incorrect";
        }
        return message;
    }
}
