package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class RecordsBean implements Serializable {

    /**
     * id : 1
     * title : 12154545
     * content : 吴系挂
     * link_status : 2
     * link : 1436864169
     * createTime : 2019-12-13 18:00:00
     */

    private String id;
    private String title;
    private String content;
    private int link_status;
    private String link;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLink_status() {
        return link_status;
    }

    public void setLink_status(int link_status) {
        this.link_status = link_status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
