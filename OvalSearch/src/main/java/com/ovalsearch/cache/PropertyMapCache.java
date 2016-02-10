/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.cache;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ovalsearch.annotation.Cache;
import com.ovalsearch.enums.Property;

/**
 * @version 1.0, 21-Dec-2015
 * @author deepanshu
 */
@Cache(name = "propertyMapCache")
public class PropertyMapCache {

    private Map<String, Object> propertiesMap = new LinkedHashMap<String, Object>();

    public Map<String, Object> getPropertiesMap() {
        return propertiesMap;
    }

    public void setPropertiesMap(Map<String, Object> propertiesMap) {
        this.propertiesMap = propertiesMap;
    }

    public void addProperty(String key, String value) {
        propertiesMap.put(key, value);
    }

    public String getPropertyString(Property property) {
        return (String) propertiesMap.get(property.getName());
    }

    public Integer getPropertyInteger(Property property) {
        return Integer.parseInt((String) propertiesMap.get(property.getName()));
    }

    public Boolean getPropertyBoolean(Property property) {
        return Boolean.parseBoolean((String) propertiesMap.get(property.getName()));
    }
}
