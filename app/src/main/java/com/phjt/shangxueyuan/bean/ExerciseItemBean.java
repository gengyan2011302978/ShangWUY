package com.phjt.shangxueyuan.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * @author: gengyan
 * date:    2021/3/16 10:53
 * company: 普华集团
 * description: 描述
 */
public class ExerciseItemBean implements MultiItemEntity {

    public static final int EXERCISE_TYPE_CHOSE = 100;
    public static final int EXERCISE_TYPE_ANALYSIS = 101;

    private String questionId;
    private String userAnswerRecordId;
    private String questionContent;
    /**
     * questionType 为null时，默认为简答题
     */
    private Integer questionType;
    private int numbType = 1;
    private int position;
    private String rightShoice;
    private String userAnswer;
    private Integer answerState;
    private String userAnswerImg;
    private String remarkContent;
    List<MineExerciseBean> commentBeans;

    public List<MineExerciseBean> getCommentBeans() {
        return commentBeans;
    }

    public int getNumbType() {
        return numbType;
    }

    public void setNumbType(int numbType) {
        this.numbType = numbType;
    }

    public void setCommentBeans(List<MineExerciseBean> commentBeans) {
        this.commentBeans = commentBeans;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private List<ExerciseItemChoseBean> optionVos;

    private List<FeedbackPictureBean> userChoseImg;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getUserAnswerRecordId() {
        return userAnswerRecordId;
    }

    public void setUserAnswerRecordId(String userAnswerRecordId) {
        this.userAnswerRecordId = userAnswerRecordId;
    }

    public Integer getQuestionType() {
        return questionType == null ? 5 : questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getRightShoice() {
        return rightShoice;
    }

    public void setRightShoice(String rightShoice) {
        this.rightShoice = rightShoice;
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

    public Integer getAnswerState() {
        return answerState == null ? 0 : answerState;
    }

    public void setAnswerState(Integer answerState) {
        this.answerState = answerState;
    }

    public String getRemarkContent() {
        return remarkContent;
    }

    public void setRemarkContent(String remarkContent) {
        this.remarkContent = remarkContent;
    }

    public List<ExerciseItemChoseBean> getOptionVos() {
        return optionVos;
    }

    public void setOptionVos(List<ExerciseItemChoseBean> optionVos) {
        this.optionVos = optionVos;
    }

    public List<FeedbackPictureBean> getUserChoseImg() {
        return userChoseImg;
    }

    public void setUserChoseImg(List<FeedbackPictureBean> userChoseImg) {
        this.userChoseImg = userChoseImg;
    }

    @Override
    public int getItemType() {
        if (questionType == null) {
            return EXERCISE_TYPE_ANALYSIS;
        } else if (questionType == 1 || questionType == 2 || questionType == 3 || questionType == 4) {
            return EXERCISE_TYPE_CHOSE;
        } else if (questionType == 5 || questionType == 6) {
            return EXERCISE_TYPE_ANALYSIS;
        }
        return EXERCISE_TYPE_ANALYSIS;
    }
}

