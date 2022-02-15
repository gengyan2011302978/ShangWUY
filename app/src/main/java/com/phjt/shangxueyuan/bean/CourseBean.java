package com.phjt.shangxueyuan.bean;

public class CourseBean {

    /**
     * id : 3
     * name : 33课程
     * coverImg : 33
     * couDesc : 课程简介
     * studyNum : 100
     * sumTimeLong : 20000
     * courseWatchDuration : 300
     */

    private String id;
    private String name;
    private String coverImg;
    private String couDesc;
    private int studyNum;
    private long sumTimeLong;
    private long courseWatchDuration;
    private int upState;

    public int getUpState() {
        return upState;
    }

    public void setUpState(int upState) {
        this.upState = upState;
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

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getCouDesc() {
        return couDesc;
    }

    public void setCouDesc(String couDesc) {
        this.couDesc = couDesc;
    }

    public int getStudyNum() {
        return studyNum;
    }

    public void setStudyNum(int studyNum) {
        this.studyNum = studyNum;
    }

    public long getSumTimeLong() {
        return sumTimeLong;
    }

    public void setSumTimeLong(int sumTimeLong) {
        this.sumTimeLong = sumTimeLong;
    }

    public long getCourseWatchDuration() {
        return courseWatchDuration;
    }

    public void setCourseWatchDuration(int courseWatchDuration) {
        this.courseWatchDuration = courseWatchDuration;
    }
}
