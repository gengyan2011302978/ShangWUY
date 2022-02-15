package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class AnnouncementListBean implements Serializable {

    /**
     * id : 1
     * title : 公告简介
     * createTime : 2020-04-01 09:17:06
     * uptateTime : 2020-04-01 09:17:06
     */

    private int id;
    private String title;
    private String createTime;
    private String uptateTime;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    private String otherId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUptateTime() {
        return uptateTime;
    }

    public void setUptateTime(String uptateTime) {
        this.uptateTime = uptateTime;
    }
}
