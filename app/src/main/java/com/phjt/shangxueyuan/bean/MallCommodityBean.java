package com.phjt.shangxueyuan.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author: gengyan
 * date:    2021/6/22 16:44
 * company: 普华集团
 * description: 商城-商品实体
 */
public class MallCommodityBean {

    @SerializedName(value = "id", alternate = "virtualCommodityId")
    private String id;
    private Integer commodityType;
    private String commodityName;
    @SerializedName(value = "studyCoin", alternate = "unitPrice")
    private Double studyCoin;
    @SerializedName(value = "exchangeNum", alternate = "quantity")
    private Integer exchangeNum;
    private String commodityDesc;
    private String commodityUrl;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCommodityType() {
        return commodityType;
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

    public Double getStudyCoin() {
        return studyCoin;
    }

    public void setStudyCoin(Double studyCoin) {
        this.studyCoin = studyCoin;
    }

    public Integer getExchangeNum() {
        return exchangeNum;
    }

    public void setExchangeNum(Integer exchangeNum) {
        this.exchangeNum = exchangeNum;
    }

    public String getCommodityDesc() {
        return commodityDesc;
    }

    public void setCommodityDesc(String commodityDesc) {
        this.commodityDesc = commodityDesc;
    }

    public String getCommodityUrl() {
        return commodityUrl;
    }

    public void setCommodityUrl(String commodityUrl) {
        this.commodityUrl = commodityUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
