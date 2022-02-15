package com.phjt.shangxueyuan.bean;

/**
 * @author: gengyan
 * date:    2020/8/11 9:24
 * company: 普华集团
 * description: 收入记录实体
 */
public class IncomeRecordBean {

    private String id;
    private String sourcePhone;
    private Double rebateMoney;
    private Integer commodityType;
    private String commodityName;
    private String createTime;
    private String integralName;
    private String integralDetail;
    private String mobile;

    public String getIntegralName() {
        return integralName;
    }

    public void setIntegralName(String integralName) {
        this.integralName = integralName;
    }

    public String getIntegralDetail() {
        return integralDetail;
    }

    public void setIntegralDetail(String integralDetail) {
        this.integralDetail = integralDetail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourcePhone() {
        return sourcePhone;
    }

    public void setSourcePhone(String sourcePhone) {
        this.sourcePhone = sourcePhone;
    }

    public Double getRebateMoney() {
        return rebateMoney == null ? 0 : rebateMoney;
    }

    public void setRebateMoney(Double rebateMoney) {
        this.rebateMoney = rebateMoney;
    }

    public Integer getCommodityType() {
        return commodityType == null ? 0 : commodityType;
    }

    public void setCommodityType(Integer commodityType) {
        this.commodityType = commodityType;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
