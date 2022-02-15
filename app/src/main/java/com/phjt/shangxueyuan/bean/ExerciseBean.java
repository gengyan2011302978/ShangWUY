package com.phjt.shangxueyuan.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.phjt.shangxueyuan.mvp.ui.adapter.ExerciseShowAdapter;

import java.util.List;

/**
 * @author: gengyan
 * date:    2021/3/19 14:51
 * company: 普华集团
 * description: 作业实体
 */
public class ExerciseBean implements MultiItemEntity {

    private String id;
    private String exerciseName;
    private Integer exerciseType;
    private String couId;
    private String couName;
    private int state;
    private int questionType;
    private String createTime;
    private List<ExerciseItemBean> questionVoList;
    @Override
    public int getItemType() {
        if (questionVoList != null) {
            return ExerciseShowAdapter.TYPE_LEVEL_0;
        }
        return ExerciseShowAdapter.TYPE_LEVEL_0;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public Integer getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(Integer exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String getCouId() {
        return couId;
    }

    public void setCouId(String couId) {
        this.couId = couId;
    }

    public String getCouName() {
        return couName;
    }

    public void setCouName(String couName) {
        this.couName = couName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<ExerciseItemBean> getQuestionVoList() {
        return questionVoList;
    }

    public void setQuestionVoList(List<ExerciseItemBean> questionVoList) {
        this.questionVoList = questionVoList;
    }
}
