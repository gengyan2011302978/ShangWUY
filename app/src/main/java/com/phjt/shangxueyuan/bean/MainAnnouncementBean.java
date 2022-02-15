package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class MainAnnouncementBean implements Serializable {

    /**
     * sign : 1
     * text : 2154545
     * title : 公告
     */

    private int sign;
    private String text;
    private String title;

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
