package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class TeacherLiveBean implements Serializable {


    /**
     * id : 7
     * channelId : 3818445
     * ticketId : 10204082
     * status : 1
     * name : 姓高名开字翼德
     * popular : 0
     * liveStartTime : null
     * teacherLiveStartTime : 1970-01-01 08:00
     * upState : 1
     * isAll : 1
     * cover : https://s1.zmengzhu.com/upload/img/94/c3/94c30c9ab91e8aab1e5f5331eefcd08e.jpeg
     * reserveState : null
     * swyPopular : null
     * liveStyle : 0
     * liveToken : VuBkpQaatskzK3xo
     */

    private String id;
    private int channelId;
    private int ticketId;
    private int status;
    private String name;
    private String popular;
    private Object liveStartTime;
    private String teacherLiveStartTime;
    private int upState;
    private int isAll;
    private String cover;
    private Object reserveState;
    private Object swyPopular;
    private Integer liveStyle;
    private String liveToken;

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

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
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

    public String getPopular() {
        return popular;
    }

    public void setPopular(String popular) {
        this.popular = popular;
    }

    public Object getLiveStartTime() {
        return liveStartTime;
    }

    public void setLiveStartTime(Object liveStartTime) {
        this.liveStartTime = liveStartTime;
    }

    public String getTeacherLiveStartTime() {
        return teacherLiveStartTime;
    }

    public void setTeacherLiveStartTime(String teacherLiveStartTime) {
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

    public Object getReserveState() {
        return reserveState;
    }

    public void setReserveState(Object reserveState) {
        this.reserveState = reserveState;
    }

    public Object getSwyPopular() {
        return swyPopular;
    }

    public void setSwyPopular(Object swyPopular) {
        this.swyPopular = swyPopular;
    }

    public Integer getLiveStyle() {
        return liveStyle == null ? 0 : liveStyle;
    }

    public void setLiveStyle(Integer liveStyle) {
        this.liveStyle = liveStyle;
    }

    public String getLiveToken() {
        return liveToken;
    }

    public void setLiveToken(String liveToken) {
        this.liveToken = liveToken;
    }
}
