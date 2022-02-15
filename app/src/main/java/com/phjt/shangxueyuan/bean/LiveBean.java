package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class LiveBean implements Serializable {

    /**
     * id : 8
     * channelId : 3818445
     * ticketId : 10204088
     * status : 0
     * name : 高开翼德
     * popular : 0
     * liveStartTime : 11:10
     * teacherLiveStartTime : null
     * upState : 1
     * isAll : 0
     * cover : https://s1.zmengzhu.com/upload/img/ba/f4/baf4c7ddc9230f880c4195a6895b4c0b.jpeg
     * reserveState : 0
     * swyPopular : null
     * liveStyle : 0
     * liveToken : null
     */

    private String id;
    private int channelId;
    private String ticketId;
    private int status;
    private String name;
    private String popular;
    private String liveStartTime;
    private Object teacherLiveStartTime;
    private int upState;
    private int isAll;
    private String cover;
    private int reserveState;
    private String swyPopular;
    private int liveStyle;
    private Object liveToken;
    private int permission;
    private Integer userPermission;
    private String studyBean;
    private String startMinute;

    public String getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(String startMinute) {
        this.startMinute = startMinute;
    }

    public String getStudyBean() {
        return studyBean;
    }

    public void setStudyBean(String studyBean) {
        this.studyBean = studyBean;
    }

    public Integer getUserPermission() {
        return userPermission == null ? 0 : userPermission;
    }

    public void setUserPermission(Integer userPermission) {
        this.userPermission = userPermission;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    /*
    0:未直播 1:直播中 2:回放 3:断流中 4直播生成回放中
     */
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

    public String getPopular() {
        return popular;
    }

    public void setPopular(String popular) {
        this.popular = popular;
    }

    public String getLiveStartTime() {
        return liveStartTime;
    }

    public void setLiveStartTime(String liveStartTime) {
        this.liveStartTime = liveStartTime;
    }

    public Object getTeacherLiveStartTime() {
        return teacherLiveStartTime;
    }

    public void setTeacherLiveStartTime(Object teacherLiveStartTime) {
        this.teacherLiveStartTime = teacherLiveStartTime;
    }

    public int getUpState() {
        return upState;
    }

    public void setUpState(int upState) {
        this.upState = upState;
    }

    public int getIsAll() {
        return isAll;
    }

    public void setIsAll(int isAll) {
        this.isAll = isAll;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getReserveState() {
        return reserveState;
    }

    public void setReserveState(int reserveState) {
        this.reserveState = reserveState;
    }

    public String getSwyPopular() {
        return swyPopular;
    }

    public void setSwyPopular(String swyPopular) {
        this.swyPopular = swyPopular;
    }

    public int getLiveStyle() {
        return liveStyle;
    }

    public void setLiveStyle(int liveStyle) {
        this.liveStyle = liveStyle;
    }

    public Object getLiveToken() {
        return liveToken;
    }

    public void setLiveToken(Object liveToken) {
        this.liveToken = liveToken;
    }
}
