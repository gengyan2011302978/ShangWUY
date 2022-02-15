package com.phjt.shangxueyuan.bean;

/**
 * @author: Roy
 * date:   2021/4/9
 * company: 普华集团
 * description:
 */
public class TrainingBattalionExchangeBean {

    private String id;
    private String code;
    private String courseDuration;
    private String createTime;
    private String courseId;
    private String trainingCampId;
    private String coverImg;
    private String name;
    private int effectiveState;
    private int upState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEffectiveState() {
        return effectiveState;
    }

    public void setEffectiveState(int effectiveState) {
        this.effectiveState = effectiveState;
    }

    public int getUpState() {
        return upState;
    }

    public void setUpState(int upState) {
        this.upState = upState;
    }

    public String getTrainingCampId() {
        return trainingCampId;
    }

    public void setTrainingCampId(String trainingCampId) {
        this.trainingCampId = trainingCampId;
    }
}
