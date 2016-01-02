/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ovalsearch.base.ApplicationsSro;
import com.ovalsearch.base.GetApplicationsResponse;
import com.ovalsearch.cache.ApplicationsCache;
import com.ovalsearch.entity.Applications;
import com.ovalsearch.services.IConverterService;
import com.ovalsearch.utils.CacheManager;

/**
 * @version 1.0, 23-Dec-2015
 * @author deepanshu
 */
@Controller
@RequestMapping("/applications")
public class ApplicationsController {

    @Autowired
    private IConverterService   converterService;

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationsController.class);

    @RequestMapping(value = "/getApplications", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public GetApplicationsResponse getApplications(@RequestParam(required = true, value = "key") String key) {
        GetApplicationsResponse response = new GetApplicationsResponse();
        if (key != null) {
            LOG.info("Request received to get applications with name : " + key);
            List<Applications> applicationsList = CacheManager.getInstance().getCache(ApplicationsCache.class).getAllMatchingApplications(key);
            response.setSros(converterService.getSroFromEntity(applicationsList));
            LOG.info("Sending response for GetApplicationsRequest {}", response);
        } else {
            LOG.info("Request received with null values. Sending null response");
        }
        return response;
    }
    
    @RequestMapping(value = "/getAppData", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ApplicationsSro getAppData(@RequestParam(required = true, value = "key") String key) {
    	ApplicationsSro response = new ApplicationsSro();
        if (key != null) {
            LOG.info("Request received to get application with apkID : " + key);
            Applications application = CacheManager.getInstance().getCache(ApplicationsCache.class).getApplicationByApkID(key);
            response = converterService.getApplicationSroFromEntity(application);
            LOG.info("Sending response for GetApplicationsRequest {}", response);
        } else {
            LOG.info("Request received with null values. Sending null response");
        }
        return response;
    }

}
