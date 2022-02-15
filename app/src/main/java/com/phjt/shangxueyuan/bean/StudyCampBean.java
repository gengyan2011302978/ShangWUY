package com.phjt.shangxueyuan.bean;

/**
 * @author: Roy
 * date:   2021/4/23
 * company: 普华集团
 * description:
 */
public class StudyCampBean {
    private String id;
    private String trainingCampName;
    private String trainingCampDesc;
    private String applyNum;
    private String taskNum;
    private String curriculumStartTime;
    private String curriculumEndTime;
    private String recruitStudentStartTime;
    private String recruitStudentEndTime;
    private String coverImg;
    private int status;
    private int registrationStatus;

    public int getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(int registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(String applyNum) {
        this.applyNum = applyNum;
    }

    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    public String getCurriculumStartTime() {
        return curriculumStartTime;
    }

    public void setCurriculumStartTime(String curriculumStartTime) {
        this.curriculumStartTime = curriculumStartTime;
    }

    public String getCurriculumEndTime() {
        return curriculumEndTime;
    }

    public void setCurriculumEndTime(String curriculumEndTime) {
        this.curriculumEndTime = curriculumEndTime;
    }

    public int getState() {
        return status;
    }

    public void setState(int state) {
        this.status = state;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getRecruitStudentStartTime() {
        return recruitStudentStartTime;
    }

    public void setRecruitStudentStartTime(String recruitStudentStartTime) {
        this.recruitStudentStartTime = recruitStudentStartTime;
    }

    public String getRecruitStudentEndTime() {
        return recruitStudentEndTime;
    }

    public void setRecruitStudentEndTime(String recruitStudentEndTime) {
        this.recruitStudentEndTime = recruitStudentEndTime;
    }
}
