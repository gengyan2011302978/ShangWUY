package com.phjt.shangxueyuan.bean;

import android.content.Intent;

import java.util.List;

/**
 * @author: Roy
 * date:   2020/10/20
 * company: 普华集团
 * description:
 */
public class TopicInfoBean {


    /**
     * id : 1
     * topicName : 1
     * focusDescribe : null
     * userId : 253
     * photo : null
     * nickName : 123
     * vipState : 0
     * themeNum : 0
     * topicFocusImg : null
     * coverImg : null
     * auditState : 1
     * publishType : 1
     * topicDescribeVo : [{"buttonName":"11","buttonUrl":"11"},{"buttonName":"22","buttonUrl":"22"}]
     */

    private String id;
    private String topicName;
    private String focusDescribe;
    private String userId;
    private String photo;
    private String nickName;
    private Integer vipState;
    private Integer freezeState;
    private Integer themeNum;
    private String topicFocusImg;
    private String coverImg;
    private int auditState;
    private int publishType;
    private List<TopicDescribeVoBean> topicDescribeVo;

    public Integer getFreezeState() {
        return freezeState;
    }

    public void setFreezeState(Integer freezeState) {
        this.freezeState = freezeState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getVipState() {
        return vipState == null ? 0 : vipState;
    }

    public void setVipState(Integer vipState) {
        this.vipState = vipState;
    }

    public int getThemeNum() {
        return themeNum;
    }

    public void setThemeNum(int themeNum) {
        this.themeNum = themeNum;
    }

    public String getTopicFocusImg() {
        return topicFocusImg;
    }

    public void setTopicFocusImg(String topicFocusImg) {
        this.topicFocusImg = topicFocusImg;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public int getAuditState() {
        return auditState;
    }

    public void setAuditState(int auditState) {
        this.auditState = auditState;
    }

    public int getPublishType() {
        return publishType;
    }

    public void setPublishType(int publishType) {
        this.publishType = publishType;
    }

    public List<TopicDescribeVoBean> getTopicDescribeVo() {
        return topicDescribeVo;
    }

    public void setTopicDescribeVo(List<TopicDescribeVoBean> topicDescribeVo) {
        this.topicDescribeVo = topicDescribeVo;
    }

    public static class TopicDescribeVoBean {
        /**
         * buttonName : 11
         * buttonUrl : 11
         */

        private String buttonName;
        private String buttonUrl;
        private String courseType;
        private String courseId;

        public String getCourseType() {
            return courseType;
        }

        public void setCourseType(String courseType) {
            this.courseType = courseType;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getButtonName() {
            return buttonName;
        }

        public void setButtonName(String buttonName) {
            this.buttonName = buttonName;
        }

        public String getButtonUrl() {
            return buttonUrl;
        }

        public void setButtonUrl(String buttonUrl) {
            this.buttonUrl = buttonUrl;
        }
    }
}
