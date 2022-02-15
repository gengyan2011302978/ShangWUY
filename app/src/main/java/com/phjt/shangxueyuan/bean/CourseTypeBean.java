package com.phjt.shangxueyuan.bean;

public class CourseTypeBean {

    /**
     * id : 0
     * name : 课程分类名称
     * level : 1
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
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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
