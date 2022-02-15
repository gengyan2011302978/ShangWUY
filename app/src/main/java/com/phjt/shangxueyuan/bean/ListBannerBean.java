package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class ListBannerBean implements Serializable {

    /**
     * id : null
     * type : null
     * name : null
     * ifUrl : 1
     * toUrl : http://www.baidu.com
     * coverUrl : http://www.baidu.com
     * sort : null
     * createTime : null
     * updateTime : null
     */

    private String id;
    private String type;
    private String name;
    private int ifUrl;
    private String toUrl;
    private String coverUrl;
    private String sort;
    private String createTime;
    private String updateTime;
    private String courseId;
    private String columnId;
    private String trainingId;
    private String liveInfoId;

    public String getLiveInfoId() {
        return liveInfoId;
    }

    public void setLiveInfoId(String liveInfoId) {
        this.liveInfoId = liveInfoId;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIfUrl() {
        return ifUrl;
    }

    public void setIfUrl(int ifUrl) {
        this.ifUrl = ifUrl;
    }

    public String getToUrl() {
        return toUrl;
    }

    public void setToUrl(String toUrl) {
        this.toUrl = toUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
