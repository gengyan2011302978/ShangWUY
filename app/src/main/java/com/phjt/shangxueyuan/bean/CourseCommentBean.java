package com.phjt.shangxueyuan.bean;

import android.content.Intent;

/**
 * @author: gengyan
 * date:    2020/5/7 11:07
 * company: 普华集团
 * description: 课程评论实体
 */
public class CourseCommentBean {

    /**
     * id : 1
     * courseId : 1
     * content : 123123
     * likeNum : 10
     * nickName : 闫大师i
     * photo : 头像
     * userStatus : 1
     * auditState； 审核状态 2未通过 0审核中 1已通过
     * img : 9宫格图片
     */

    private String id;
    private String courseId;
    private String content;
    private Integer likeNum;
    private String nickName;
    private String photo;
    private Integer userStatus;
    private Integer auditState;
    private String img;
    private Integer backNum;
    private Integer vipState;
    private String userId;

    public Integer getBackNum() {
        return backNum == null ? 0 : backNum;
    }

    public void setBackNum(Integer backNum) {
        this.backNum = backNum;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLikeNum() {
        return likeNum == null ? 0 : likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getUserStatus() {
        return userStatus == null ? 0 : userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getVipState() {
        return vipState == null ? 0 : vipState;
    }

    public void setVipState(Integer vipState) {
        this.vipState = vipState;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
