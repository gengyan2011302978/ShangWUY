package com.phjt.shangxueyuan.bean;

/**
 * @author: gengyan
 * date:    2020/11/24 11:42
 * company: 普华集团
 * description: 描述
 */
public class CouponBean {

    private String id;
    private String couponName;
    private int useControl;
    private String useControlPrice;
    private int couponContent;
    private double couponContentPrice;
    private int issueNum;
    private String useExplain;
    private int accessRulesNum;
    private int applyCommodity;
    private String gainStartTime;
    private String gainEntTime;
    private int useTime;
    private int jumpType;
    private String useStartTime;
    private String useEntTime;
    private String couponFailureTime;
    private double optimalAmount;
    private String userCouponId;
    private boolean isChose;

    public String getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(String userCouponId) {
        this.userCouponId = userCouponId;
    }

    public double getOptimalAmount() {
        return optimalAmount;
    }

    public void setOptimalAmount(double optimalAmount) {
        this.optimalAmount = optimalAmount;
    }

    public boolean isChose() {
        return isChose;
    }

    public void setChose(boolean chose) {
        isChose = chose;
    }

    public String getCouponFailureTime() {
        return couponFailureTime;
    }

    public void setCouponFailureTime(String couponFailureTime) {
        this.couponFailureTime = couponFailureTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getUseControl() {
        return useControl;
    }

    public void setUseControl(int useControl) {
        this.useControl = useControl;
    }

    public String getUseControlPrice() {
        return useControlPrice;
    }

    public void setUseControlPrice(String useControlPrice) {
        this.useControlPrice = useControlPrice;
    }

    public int getCouponContent() {
        return couponContent;
    }

    public void setCouponContent(int couponContent) {
        this.couponContent = couponContent;
    }

    public double getCouponContentPrice() {
        return couponContentPrice;
    }

    public void setCouponContentPrice(double couponContentPrice) {
        this.couponContentPrice = couponContentPrice;
    }

    public int getIssueNum() {
        return issueNum;
    }

    public void setIssueNum(int issueNum) {
        this.issueNum = issueNum;
    }

    public String getUseExplain() {
        return useExplain;
    }

    public void setUseExplain(String useExplain) {
        this.useExplain = useExplain;
    }

    public int getAccessRulesNum() {
        return accessRulesNum;
    }

    public void setAccessRulesNum(int accessRulesNum) {
        this.accessRulesNum = accessRulesNum;
    }

    public int getApplyCommodity() {
        return applyCommodity;
    }

    public void setApplyCommodity(int applyCommodity) {
        this.applyCommodity = applyCommodity;
    }

    public String getGainStartTime() {
        return gainStartTime;
    }

    public void setGainStartTime(String gainStartTime) {
        this.gainStartTime = gainStartTime;
    }

    public String getGainEntTime() {
        return gainEntTime;
    }

    public void setGainEntTime(String gainEntTime) {
        this.gainEntTime = gainEntTime;
    }

    public int getUseTime() {
        return useTime;
    }

    public void setUseTime(int useTime) {
        this.useTime = useTime;
    }

    public String getUseStartTime() {
        return useStartTime;
    }

    public void setUseStartTime(String useStartTime) {
        this.useStartTime = useStartTime;
    }

    public String getUseEntTime() {
        return useEntTime;
    }

    public void setUseEntTime(String useEntTime) {
        this.useEntTime = useEntTime;
    }

    public int getJumpType() {
        return jumpType;
    }

    public void setJumpType(int jumpType) {
        this.jumpType = jumpType;
    }
}
