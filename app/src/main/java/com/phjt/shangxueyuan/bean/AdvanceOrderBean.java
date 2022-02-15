package com.phjt.shangxueyuan.bean;

/**
 * Created by ptt on 2019/4/12.
 * 我的订单 订单详情 --> bean
 */
public class AdvanceOrderBean {
    private WXPerOrder wxPerOrder;
    private String aliPerOrder;


    public WXPerOrder getWxPerOrder() {
        return wxPerOrder;
    }

    public void setWxPerOrder(WXPerOrder wxPerOrder) {
        this.wxPerOrder = wxPerOrder;
    }

    public String getAliPerOrder() {
        return aliPerOrder;
    }

    public void setAliPerOrder(String aliPerOrder) {
        this.aliPerOrder = aliPerOrder;
    }

    public class WXPerOrder {
        private String sign;
        private String timestamp;
        private String noncestr;
        private String partnerid;
        private String prepayid;
        private String package_wx;
        private String appid_wx;
        private String extdata;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackage_wx() {
            return package_wx;
        }

        public void setPackage_wx(String package_wx) {
            this.package_wx = package_wx;
        }

        public String getAppid_wx() {
            return appid_wx;
        }

        public void setAppid_wx(String appid_wx) {
            this.appid_wx = appid_wx;
        }

        public String getExtdata() {
            return extdata;
        }

        public void setExtdata(String extdata) {
            this.extdata = extdata;
        }
    }
}
