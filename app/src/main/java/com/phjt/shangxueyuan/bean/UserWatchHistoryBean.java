package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class UserWatchHistoryBean implements Serializable {


    /**
     * courseName : 课程1
     * pointId : 10
     * pointName : 视频10小节名称
     * id : 1
     * courseId : 1
     */

    private String courseName;
    private int pointId;
    private String pointName;
    private int id;
    private String courseId;
    private int upState;
    private String columnId;
    private String couverImg;

    public String getCouverImg() {
        return couverImg;
    }

    public void setCouverImg(String couverImg) {
        this.couverImg = couverImg;
    }

    public void setUpState(int upState) {
        this.upState = upState;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getUpState() {
        return upState;
    }

    public void setUpState(Integer upState) {
        this.upState = upState;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }
}
