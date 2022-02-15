package com.phjt.shangxueyuan.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.phjt.shangxueyuan.mvp.ui.adapter.TaskCurrencyAdapter;

import java.util.List;

/**
 * @author: gengyan
 * date:    2021/6/28 16:46
 * company: 普华集团
 * description: 学豆任务 - 实体一
 */
public class TaskCurrencyFirstBean extends AbstractExpandableItem<TaskCurrencySecondBean> implements MultiItemEntity {

    private String maxLimit;
    private String typeName;
    private List<TaskCurrencySecondBean> taskList;
    private Integer type;

    public String getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(String maxLimit) {
        this.maxLimit = maxLimit;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<TaskCurrencySecondBean> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskCurrencySecondBean> taskList) {
        this.taskList = taskList;
    }

    public Integer getType() {
        return type == null ? 0 : type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return TaskCurrencyAdapter.TYPE_TASK_FIRST;
    }
}
