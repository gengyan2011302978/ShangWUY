package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class ReleaseCoverBean implements Serializable {


    /**
     * id : 8
     * imgUrl : 图片链接
     */

    private int id;
    private String imgUrl;
    private int checkStatus = 2;

    public int getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
