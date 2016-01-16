/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @version 1.0, 17-Dec-2015
 * @author deepanshu
 */
@Entity
@Table(name = "applications")
public class Applications extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 704383292130715830L;

    private String            name;
    private String            path;
    private String            icon;
    private String            iconHd;
    private Integer           downloads;
    private Float             rating;
    private Integer           size;
    private String            category;
    private String            apkId;
    private String            alternateUrl;

    @Column(name = "name", length = 256, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "path", length = 512, nullable = false)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column(name = "icon", length = 1024, nullable = true)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name = "icon_hd", length = 1024, nullable = true)
    public String getIconHd() {
        return iconHd;
    }

    public void setIconHd(String iconHd) {
        this.iconHd = iconHd;
    }

    @Column(name = "downloads", length = 11, nullable = true)
    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Column(name = "rating", nullable = true)
    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    @Column(name = "size", length = 20, nullable = true)
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Column(name = "category", length = 128, nullable = true)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column(name = "apk_id", length = 128, nullable = false)
    public String getApkId() {
        return apkId;
    }

    public void setApkId(String apkId) {
        this.apkId = apkId;
    }

    @Column(name = "alternate_url", length = 512, nullable = true)
    public String getAlternateUrl() {
        return alternateUrl;
    }

    public void setAlternateUrl(String alternateUrl) {
        this.alternateUrl = alternateUrl;
    }

    @Override
    public String toString() {
        return "Applications [name=" + name + ", path=" + path + ", icon=" + icon + ", iconHd=" + iconHd + ", downloads=" + downloads + ", rating=" + rating + ", size=" + size
                + ", category=" + category + ", apkId=" + apkId + ", alternateUrl=" + alternateUrl + "]";
    }

}
