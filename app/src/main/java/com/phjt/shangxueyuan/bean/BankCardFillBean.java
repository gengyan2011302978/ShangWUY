package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

/**
 * @author: gengyan
 * date:    2020/8/11 11:00
 * company: 普华集团
 * description: 银行卡信息填写实体
 */
public class BankCardFillBean implements Serializable {

    private String cardholder;
    private String cardNumber;
    private String bankName;
    private String bankAddress;
    private String cardholderPhone;

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getCardholderPhone() {
        return cardholderPhone;
    }

    public void setCardholderPhone(String cardholderPhone) {
        this.cardholderPhone = cardholderPhone;
    }
}
