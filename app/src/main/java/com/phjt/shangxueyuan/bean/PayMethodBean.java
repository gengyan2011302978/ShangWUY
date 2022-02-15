package com.phjt.shangxueyuan.bean;


public class PayMethodBean {
    /**
     * name : 是否开启微信支付
     * code : WX_PAY_STATUS
     * vlaue : 2
     */

    private String name;
    private String code;
    private String vlaue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVlaue() {
        return vlaue;
    }

    public void setVlaue(String vlaue) {
        this.vlaue = vlaue;
    }
}
