package com.phjt.shangxueyuan.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.phjt.shangxueyuan.mvp.ui.adapter.TaskCurrencyAdapter;

/**
 * @author: gengyan
 * date:    2021/6/28 16:46
 * company: 普华集团
 * description: 学豆任务 - 实体二
 */
public class TaskCurrencySecondBean implements MultiItemEntity {

    private String integralNum;
    private String integralName;
    private Integer finishFlag;
    private String id;
    private int type;
    private String smallTypeMaxLimit;
    private String integralFrequency;
    private Integer integralType;
    private String integralTotal;
    private String finishDesc;
    private String finishNum;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(String finishNum) {
        this.finishNum = finishNum;
    }

    public String getIntegralFrequency() {
        return integralFrequency;
    }

    public void setIntegralFrequency(String integralFrequency) {
        this.integralFrequency = integralFrequency;
    }

    public String getIntegralNum() {
        return integralNum;
    }

    public void setIntegralNum(String integralNum) {
        this.integralNum = integralNum;
    }

    public String getIntegralName() {
        return integralName;
    }

    public void setIntegralName(String integralName) {
        this.integralName = integralName;
    }

    public Integer getFinishFlag() {
        return finishFlag;
    }

    public void setFinishFlag(Integer finishFlag) {
        this.finishFlag = finishFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSmallTypeMaxLimit() {
        return smallTypeMaxLimit;
    }

    public void setSmallTypeMaxLimit(String smallTypeMaxLimit) {
        this.smallTypeMaxLimit = smallTypeMaxLimit;
    }

    public Integer getIntegralType() {
        return integralType;
    }

    public void setIntegralType(Integer integralType) {
        this.integralType = integralType;
    }

    public String getIntegralTotal() {
        return integralTotal;
    }

    public void setIntegralTotal(String integralTotal) {
        this.integralTotal = integralTotal;
    }

    public String getFinishDesc() {
        return finishDesc;
    }

    public void setFinishDesc(String finishDesc) {
        this.finishDesc = finishDesc;
    }

    @Override
    public int getItemType() {
        return TaskCurrencyAdapter.TYPE_TASK_SECOND;
    }
}
