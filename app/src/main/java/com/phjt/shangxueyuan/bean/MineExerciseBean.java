package com.phjt.shangxueyuan.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * @author: Roy
 * date:   2021/3/19
 * company: 普华集团
 * description:
 */
public class MineExerciseBean implements Serializable {

    private String id;
    private String exerciseName;
    private String couName;
    private String couId;
    private String exerciseBookId;
    private int state;

    public String getExerciseBookId() {
        return TextUtils.isEmpty(exerciseBookId) ? "0" : exerciseBookId;
    }

    public void setExerciseBookId(String exerciseBookId) {
        this.exerciseBookId = exerciseBookId;
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

    public String getCouName() {
        return couName;
    }

    public void setCouName(String couName) {
        this.couName = couName;
    }

    public String getCouId() {
        return couId;
    }

    public void setCouId(String couId) {
        this.couId = couId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
