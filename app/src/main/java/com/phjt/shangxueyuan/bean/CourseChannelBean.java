package com.phjt.shangxueyuan.bean;

import java.util.List;

public class CourseChannelBean {

    /**
     * id : 0
     * name : 主修课
     * level : 2
     * state : 0
     * sumTimeLong : 20000
     * couTypeWatchDuration : 300
     */

    private String id;
    private String name;
    private int level;
    private int state;
    private long sumTimeLong;
    private long couTypeWatchDuration;
    private List<CourseBean> courseRecord;

    public List<CourseBean> getCourseRecord() {
        return courseRecord;
    }

    public void setCourseRecord(List<CourseBean> courseRecord) {
        this.courseRecord = courseRecord;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getSumTimeLong() {
        return sumTimeLong;
    }

    public void setSumTimeLong(int sumTimeLong) {
        this.sumTimeLong = sumTimeLong;
    }

    public long getCouTypeWatchDuration() {
        return couTypeWatchDuration;
    }

    public void setCouTypeWatchDuration(int couTypeWatchDuration) {
        this.couTypeWatchDuration = couTypeWatchDuration;
    }
}
