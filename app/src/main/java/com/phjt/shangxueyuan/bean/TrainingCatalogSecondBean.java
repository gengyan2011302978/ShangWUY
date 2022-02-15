package com.phjt.shangxueyuan.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.phjt.shangxueyuan.mvp.ui.adapter.CourseCatalogAdapter;
import com.phjt.shangxueyuan.mvp.ui.adapter.TrainingCatalogAdapter;

import java.io.Serializable;

/**
 * @author: gengyan
 * date:    2020/3/27 18:54
 * company: 普华集团
 * description: 描述
 */
public class TrainingCatalogSecondBean implements MultiItemEntity, Serializable {

    private String id;
    private String nodeId;
    private String otherId;
    private String otherName;
    private Integer taskType;
    private Integer weight;
    private String createTime;
    private String updateTime;
    private Integer readNum;
    private Integer unlockState;
    private Integer tryRead;
    private String videoId;
    private String punchCardId;
    private Long lastWatchTime;
    private Integer exrciseState;
    private String exerciseBookId;

    public String getExerciseBookId() {
        return exerciseBookId;
    }

    public void setExerciseBookId(String exerciseBookId) {
        this.exerciseBookId = exerciseBookId;
    }

    public Integer getExrciseState() {
        return exrciseState == null ? 0 : exrciseState;
    }

    public void setExrciseState(Integer exrciseState) {
        this.exrciseState = exrciseState;
    }

    public Long getLastWatchTime() {
        return lastWatchTime == null ? 0 : lastWatchTime;
    }

    public void setLastWatchTime(Long lastWatchTime) {
        this.lastWatchTime = lastWatchTime;
    }

    public String getPunchCardId() {
        return punchCardId;
    }

    public void setPunchCardId(String punchCardId) {
        this.punchCardId = punchCardId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Integer getTryRead() {
        return tryRead == null ? 0 : tryRead;
    }

    public void setTryRead(Integer tryRead) {
        this.tryRead = tryRead;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public Integer getTaskType() {
        return taskType == null ? 0 : taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getWeight() {
        return weight == null ? 0 : weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getReadNum() {
        return readNum == null ? 0 : readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public Integer getUnlockState() {
        return unlockState == null ? 0 : unlockState;
    }

    public void setUnlockState(Integer unlockState) {
        this.unlockState = unlockState;
    }

    @Override
    public int getItemType() {
        return TrainingCatalogAdapter.TYPE_LEVEL_1;
    }
}
