package com.phjt.shangxueyuan.bean;

/**
 * @author: Roy
 * date:   2020/10/29
 * company: 普华集团
 * description:
 */
public class MyTopicBean {
    private int id;
    private String coverImg;
    private String topicName;
    private String focusDescribe;
    private String topicDynamicNum;
    private String upState;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getFocusDescribe() {
        return focusDescribe;
    }

    public void setFocusDescribe(String focusDescribe) {
        this.focusDescribe = focusDescribe;
    }

    public String getTopicDynamicNum() {
        return topicDynamicNum;
    }

    public void setTopicDynamicNum(String topicDynamicNum) {
        this.topicDynamicNum = topicDynamicNum;
    }

    public String getUpState() {
        return upState;
    }

    public void setUpState(String upState) {
        this.upState = upState;
    }
}
