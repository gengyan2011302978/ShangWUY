package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class FileLevelBean implements Serializable {

    private String activityName;
    private String activityDesc;
    private String activityUrl;
    private int activityStatus;
    private int activityTotal;
    private long activityTotalSize;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
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

    public int getActivityTotal() {
        return activityTotal;
    }

    public void setActivityTotal(int activityTotal) {
        this.activityTotal = activityTotal;
    }

    public long getActivityTotalSize() {
        return activityTotalSize;
    }

    public void setActivityTotalSize(long activityTotalSize) {
        this.activityTotalSize = activityTotalSize;
    }
}
