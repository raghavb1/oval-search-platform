/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.base;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @version 1.0, 22-Dec-2015
 * @author deepanshu
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetApplicationsRequest implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6022100205925994334L;

    private String            name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
