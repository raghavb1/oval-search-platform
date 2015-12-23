/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.base;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @version 1.0, 22-Dec-2015
 * @author deepanshu
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetApplicationsResponse implements Serializable {

    /**
     * 
     */
    private static final long     serialVersionUID = 2886975009548183840L;

    private List<ApplicationsSro> sros;

    public List<ApplicationsSro> getSros() {
        return sros;
    }

    public void setSros(List<ApplicationsSro> sros) {
        this.sros = sros;
    }

    @Override
    public String toString() {
        return "GetApplicationsResponse [sros=" + sros + "]";
    }

}
