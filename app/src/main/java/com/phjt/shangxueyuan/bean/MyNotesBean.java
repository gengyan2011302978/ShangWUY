package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

/**
 * @author Roy
 * date   2020/6/1
 * company 普华集团
 * description:
 */
public class MyNotesBean implements Serializable {

    private int id;
    private int courseId;
    private int pointId;
    private String noteContent;
    private int openState;
    private int backNum;
    private String photo;
    private String nickName;
    private String pointName;
    private int auditState;
    private int upState;
    private int notesLikeNum;
    private int likeState;
    private int editState;
    private long coursePauseTime;
    private String notesImg;
    private String creatTime;
    private String userId;
    private Integer vipState;
    private String couType;

    public String getCouType() {
        return couType;
    }

    public void setCouType(String couType) {
        this.couType = couType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getVipState() {
        return vipState == null ? 0 : vipState;
    }

    public void setVipState(Integer vipState) {
        this.vipState = vipState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public int getOpenState() {
        return openState;
    }

    public void setOpenState(int openState) {
        this.openState = openState;
    }

    public int getBackNum() {
        return backNum;
    }

    public void setBackNum(int backNum) {
        this.backNum = backNum;
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

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getAuditState() {
        return auditState;
    }

    public void setAuditState(int auditState) {
        this.auditState = auditState;
    }

    public int getUpState() {
        return upState;
    }

    public void setUpState(int upState) {
        this.upState = upState;
    }

    public int getNotesLikeNum() {
        return notesLikeNum;
    }

    public void setNotesLikeNum(int notesLikeNum) {
        this.notesLikeNum = notesLikeNum;
    }

    public int getLikeState() {
        return likeState;
    }

    public void setLikeState(int likeState) {
        this.likeState = likeState;
    }

    public long getCoursePauseTime() {
        return coursePauseTime;
    }

    public void setCoursePauseTime(long coursePauseTime) {
        this.coursePauseTime = coursePauseTime;
    }

    public String getNotesImg() {
        return notesImg;
    }

    public void setNotesImg(String notesImg) {
        this.notesImg = notesImg;
    }

    public int getEditState() {
        return editState;
    }

    public void setEditState(int editState) {
        this.editState = editState;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }
}
