package com.phjt.shangxueyuan.bean;

import java.util.List;

public class OrdinaryCourseBean {

    /**
     * id : 0
     * name : 创富通道
     * level : 1
     */

    private int id;
    private String name;
    private int level;
    private List<CourseBean> recordList;

    public List<CourseBean> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<CourseBean> recordList) {
        this.recordList = recordList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
