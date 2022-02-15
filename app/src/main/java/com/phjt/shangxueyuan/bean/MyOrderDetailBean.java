package com.phjt.shangxueyuan.bean;

/**
 * Created by ptt on 2019/4/12.
 * 我的订单 订单详情 --> bean
 */
public class MyOrderDetailBean {

    /**
     * commodityId : 1
     * commodityMoney : 333
     * commodityName : VIP月卡
     * id : 9
     * orderNo : 202003311755445286
     * orderTime : 1585648544923
     * payMethod : 1
     * status : 1
     * userId : 14
     */

    private int commodityId;
    private float commodityMoney;
    private String commodityName;
    private int id;
    private String orderNo;
    private long orderTime;
    private int payMethod;
    private int status;
    private int userId;

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public float getCommodityMoney() {
        return commodityMoney;
    }

    public void setCommodityMoney(float commodityMoney) {
        this.commodityMoney = commodityMoney;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
