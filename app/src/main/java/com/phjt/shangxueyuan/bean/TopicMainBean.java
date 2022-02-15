package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class TopicMainBean implements Serializable {


    /**
     * id : 3
     * topicName : 3
     * viewNum : 0
     * coverImg : null
     * themeNum : null
     * focusDescribe : null
     * upState : null
     */

    private int id;
    private String topicName;
    private int viewNum;
    private String coverImg;
    private String themeNum;
    private String focusDescribe;
    private String upState;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getThemeNum() {
        return themeNum;
    }

    public void setThemeNum(String themeNum) {
        this.themeNum = themeNum;
    }

    public String getFocusDescribe() {
        return focusDescribe;
    }

    public void setFocusDescribe(String focusDescribe) {
        this.focusDescribe = focusDescribe;
    }

    public String getUpState() {
        return upState;
    }

    public void setUpState(String upState) {
        this.upState = upState;
    }
}
