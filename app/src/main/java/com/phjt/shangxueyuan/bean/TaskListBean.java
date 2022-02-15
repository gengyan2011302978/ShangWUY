package com.phjt.shangxueyuan.bean;

/**
 * @author: Roy
 * date:   2020/10/28
 * company: 普华集团
 * description:
 */
public class TaskListBean {
    private String id;
    private String integralName;
    private int integralType;
    private int integralNum;
    private int integralFrequency;
    private int finishNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntegralName() {
        return integralName;
    }

    public void setIntegralName(String integralName) {
        this.integralName = integralName;
    }

    public int getIntegralType() {
        return integralType;
    }

    public void setIntegralType(int integralType) {
        this.integralType = integralType;
    }

    public int getIntegralNum() {
        return integralNum;
    }

    public void setIntegralNum(int integralNum) {
        this.integralNum = integralNum;
    }

    public int getIntegralFrequency() {
        return integralFrequency;
    }

    public void setIntegralFrequency(int integralFrequency) {
        this.integralFrequency = integralFrequency;
    }

    public int getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(int finishNum) {
        this.finishNum = finishNum;
    }
}
