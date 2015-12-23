/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */  
package com.ovalsearch.cache;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ovalsearch.annotation.Cache;
import com.ovalsearch.entity.Applications;

/**
 *  
 *  @version     1.0, 22-Dec-2015
 *  @author deepanshu
 */
@Cache(name = "applicationsCache")
public class ApplicationsCache {

    private Map<String, Applications> dataMap = new LinkedHashMap<String, Applications>();

    public Map<String, Applications> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Applications> dataMap) {
        this.dataMap = dataMap;
    }
    
    public void addApplication(Applications applications){
        dataMap.put(applications.getName().toLowerCase(), applications);
    }
    
    public Applications getApplicationsByName(String applicationName){
        return dataMap.get(applicationName.toLowerCase());
    }
    
    public List<Applications> getAllMatchingApplications(String key){
        List<Applications> result = new ArrayList<Applications>();
        for(String name : dataMap.keySet()){
            if(name.contains(key.toLowerCase())){
                result.add(dataMap.get(name));
            }
        }
        return result;
    }
}
