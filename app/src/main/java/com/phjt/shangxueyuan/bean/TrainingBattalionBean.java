package com.phjt.shangxueyuan.bean;

/**
 * @author: Roy
 * date:   2021/2/1
 * company: 普华集团
 * description:
 */
public class TrainingBattalionBean {
    private int id;
    private String createTime;
    private String coverImg;
    private String trainingCampName;
    private String trainingCampDesc;
    private Integer applyCount;
    private long taskNum;
    private long completeTaskNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getTrainingCampName() {
        return trainingCampName;
    }

    public void setTrainingCampName(String trainingCampName) {
        this.trainingCampName = trainingCampName;
    }

    public String getTrainingCampDesc() {
        return trainingCampDesc;
    }

    public void setTrainingCampDesc(String trainingCampDesc) {
        this.trainingCampDesc = trainingCampDesc;
    }

    public Integer getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(Integer applyCount) {
        this.applyCount = applyCount;
    }

    public long getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(long taskNum) {
        this.taskNum = taskNum;
    }

    public long getCompleteTaskNum() {
        return completeTaskNum;
    }

    public void setCompleteTaskNum(long completeTaskNum) {
        this.completeTaskNum = completeTaskNum;
    }
}
