package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class ProdInfoBean implements Serializable {


    /**
     * email : yanjianjun@blhcn.com
     * wx : Tdingdong
     * address :
     * qrCode : https://prod-shangwuyou-oss.sinobalanceol.com/images/1915907172296658.jpg
     */

    private String email;
    private String wx;
    private String address;
    private String qrCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
