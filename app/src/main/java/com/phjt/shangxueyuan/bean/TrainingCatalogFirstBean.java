package com.phjt.shangxueyuan.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.phjt.shangxueyuan.mvp.ui.adapter.TrainingCatalogAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * @author: gengyan
 * date:    2020/3/27 18:53
 * company: 普华集团
 * description: 描述
 */
public class TrainingCatalogFirstBean extends AbstractExpandableItem<TrainingCatalogSecondBean> implements MultiItemEntity, Serializable {

    private String id;
    private String trainingCampId;
    private String nodeName;
    private Integer taskNum;
    private Integer weight;
    private String createTime;
    private String updateTime;
    private Integer unlockState;
    private String unLockDate;
    private Integer lastWatch;

    private List<TrainingCatalogSecondBean> taskList;

    public Integer getLastWatch() {
        return lastWatch == null ? 0 : lastWatch;
    }

    public void setLastWatch(Integer lastWatch) {
        this.lastWatch = lastWatch;
    }

    public String getUnLockDate() {
        return unLockDate;
    }

    public void setUnLockDate(String unLockDate) {
        this.unLockDate = unLockDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainingCampId() {
        return trainingCampId;
    }

    public void setTrainingCampId(String trainingCampId) {
        this.trainingCampId = trainingCampId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getTaskNum() {
        return taskNum == null ? 0 : taskNum;
    }

    public void setTaskNum(Integer taskNum) {
        this.taskNum = taskNum;
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

    public Integer getUnlockState() {
        return unlockState;
    }

    public void setUnlockState(Integer unlockState) {
        this.unlockState = unlockState;
    }

    public List<TrainingCatalogSecondBean> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TrainingCatalogSecondBean> taskList) {
        this.taskList = taskList;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return TrainingCatalogAdapter.TYPE_LEVEL_0;
    }
}
