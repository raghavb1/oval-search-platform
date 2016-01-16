/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.base;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @version 1.0, 23-Dec-2015
 * @author deepanshu
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationsSro implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2292700227938847763L;

    private String            name;
    private String            path;
    private String            icon;
    private String            iconHd;
    private Integer           downloads;
    private Float             rating;
    private Integer           size;
    private String            category;
    private String            apkid;
    private String            alternateUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconHd() {
        return iconHd;
    }

    public void setIconHd(String iconHd) {
        this.iconHd = iconHd;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getApkid() {
        return apkid;
    }

    public void setApkid(String apkid) {
        this.apkid = apkid;
    }

    public String getAlternateUrl() {
        return alternateUrl;
    }

    public void setAlternateUrl(String alternateUrl) {
        this.alternateUrl = alternateUrl;
    }

    @Override
    public String toString() {
        return "ApplicationsSro [name=" + name + ", path=" + path + ", icon=" + icon + ", iconHd=" + iconHd + ", downloads=" + downloads + ", rating=" + rating + ", size=" + size
                + ", category=" + category + ", apkid=" + apkid + ", alternateUrl=" + alternateUrl + "]";
    }

}
