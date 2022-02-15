package com.phjt.shangxueyuan.bean;

/**
 * @author: gengyan
 * date:    2020/8/11 11:22
 * company: 普华集团
 * description: 钱包页面金额
 */
public class WalletMoneyBean {

    public Double userAsset;
    public Double alreadyWithdrawAsset;
    public Double leastAsset;

    public Double getUserAsset() {
        return userAsset == null ? 0 : userAsset;
    }

    public void setUserAsset(Double userAsset) {
        this.userAsset = userAsset;
    }

    public Double getAlreadyWithdrawAsset() {
        return alreadyWithdrawAsset == null ? 0 : alreadyWithdrawAsset;
    }

    public void setAlreadyWithdrawAsset(Double alreadyWithdrawAsset) {
        this.alreadyWithdrawAsset = alreadyWithdrawAsset;
    }

    public void setLeastAsset(Double leastAsset) {
        this.leastAsset = leastAsset;
    }

    public Double getLeastAsset() {
        return leastAsset == null ? 0 : leastAsset;
    }
}
