package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class ActivityInfoBean implements Serializable {

    /**
     * id : 1
     * activityName : 活动1
     * activityUrl : baidu.om
     * activityStatus : 1
     * createTime : 2020-04-29 07:26:15
     * updateTime : 2020-04-29 07:09:18
     */

    private int id;
    private String activityName;
    private String activityUrl;
    private int activityStatus;
    private String createTime;
    private String updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }

    public int getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(int activityStatus) {
        this.activityStatus = activityStatus;
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
