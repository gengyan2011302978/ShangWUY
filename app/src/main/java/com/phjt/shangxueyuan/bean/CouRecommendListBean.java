package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class CouRecommendListBean implements Serializable {

    /**
     * id : 8
     * channelId : 频道ID
     * ticketId : 视频ID
     * status : 0:未直播 1:直播中 2:回放 3:断流中 4直播生成回放中
     * name : 333333333
     * swyPopular : 33
     * liveStartTime :
     * upState :
     * isAll :
     * cover :
     * nickName :
     */

    private int id;
    private String channelId;
    private String ticketId;
    private int liveStyle;
    private int status;
    private String name;
    private String couId;
    private String swyPopular;
    private String popular;
    private String liveStartTime;
    private String upState;
    private String isAll;
    private String cover;
    private String coverImg;
    private String coverImgVertical;
    private String nickName;
    private String studyNum;
    private String typeName;
    private String coursewareName;
    private String teacherName;
    private String newStates;
    private String couDesc;
    private String freeType;

    public String getPopular() {
        return popular;
    }

    public void setPopular(String popular) {
        this.popular = popular;
    }

    public String getCouId() {
        return couId;
    }

    public void setCouId(String couId) {
        this.couId = couId;
    }

    public String getCoverImgVertical() {
        return coverImgVertical;
    }

    public void setCoverImgVertical(String coverImgVertical) {
        this.coverImgVertical = coverImgVertical;
    }

    public int getLiveStyle() {
        return liveStyle;
    }

    public void setLiveStyle(int liveStyle) {
        this.liveStyle = liveStyle;
    }

    public String getFreeType() {
        return freeType;
    }

    public void setFreeType(String freeType) {
        this.freeType = freeType;
    }

    public String getCouDesc() {
        return couDesc;
    }

    public void setCouDesc(String couDesc) {
        this.couDesc = couDesc;
    }

    public String getNewStates() {
        return newStates;
    }

    public void setNewStates(String newStates) {
        this.newStates = newStates;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCoursewareName() {
        return coursewareName;
    }

    public void setCoursewareName(String coursewareName) {
        this.coursewareName = coursewareName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getStudyNum() {
        return studyNum;
    }

    public void setStudyNum(String studyNum) {
        this.studyNum = studyNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSwyPopular() {
        return swyPopular;
    }

    public void setSwyPopular(String swyPopular) {
        this.swyPopular = swyPopular;
    }

    public String getLiveStartTime() {
        return liveStartTime;
    }

    public void setLiveStartTime(String liveStartTime) {
        this.liveStartTime = liveStartTime;
    }

    public String getUpState() {
        return upState;
    }

    public void setUpState(String upState) {
        this.upState = upState;
    }

    public String getIsAll() {
        return isAll;
    }

    public void setIsAll(String isAll) {
        this.isAll = isAll;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
