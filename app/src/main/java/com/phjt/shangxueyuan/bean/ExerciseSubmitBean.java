package com.phjt.shangxueyuan.bean;

/**
 * @author: gengyan
 * date:    2021/3/22 17:16
 * company: 普华集团
 * description: 作业提交实体
 */
public class ExerciseSubmitBean {

    private String userAnswerRecordId;
    private String exerciseId;
    private Integer exerciseType;
    private String questionId;
    private String userAnswer;
    private String userAnswerImg;
    private int questionType;
    private String couId;
    private String trainingId;
    private String nodeTaskLinkId;

    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }

    public String getNodeTaskLinkId() {
        return nodeTaskLinkId;
    }

    public void setNodeTaskLinkId(String nodeTaskLinkId) {
        this.nodeTaskLinkId = nodeTaskLinkId;
    }

    public String getUserAnswerRecordId() {
        return userAnswerRecordId;
    }

    public void setUserAnswerRecordId(String userAnswerRecordId) {
        this.userAnswerRecordId = userAnswerRecordId;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Integer getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(Integer exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getUserAnswerImg() {
        return userAnswerImg;
    }

    public void setUserAnswerImg(String userAnswerImg) {
        this.userAnswerImg = userAnswerImg;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getCouId() {
        return couId;
    }

    public void setCouId(String couId) {
        this.couId = couId;
    }
}
