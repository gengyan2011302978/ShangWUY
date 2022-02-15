package com.phjt.shangxueyuan.bean;

/**
 * @author: gengyan
 * date:    2020/8/11 9:56
 * company: 普华集团
 * description: 提现记录
 */
public class WithdrawalRecordBean {

    private String id;
    private String userId;
    private Double withdrawMoney;
    private String applyTime;
    private Integer auditState;
    private Integer withdrawType;
    private String withdrawAccountInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getWithdrawMoney() {
        return withdrawMoney == null ? 0 : withdrawMoney;
    }

    public void setWithdrawMoney(Double withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getAuditState() {
        return auditState == null ? 0 : auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    public Integer getWithdrawType() {
        return withdrawType == null ? 0 : withdrawType;
    }

    public void setWithdrawType(Integer withdrawType) {
        this.withdrawType = withdrawType;
    }

    public String getWithdrawAccountInfo() {
        return withdrawAccountInfo;
    }

    public void setWithdrawAccountInfo(String withdrawAccountInfo) {
        this.withdrawAccountInfo = withdrawAccountInfo;
    }

    public static class WithdrawAccountInfoBean {

        private String bankAddress;
        private String bankName;
        private String cardNumber;
        private String cardholder;
        private String cardholderPhone;

        public String getBankAddress() {
            return bankAddress;
        }

        public void setBankAddress(String bankAddress) {
            this.bankAddress = bankAddress;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getCardholder() {
            return cardholder;
        }

        public void setCardholder(String cardholder) {
            this.cardholder = cardholder;
        }

        public String getCardholderPhone() {
            return cardholderPhone;
        }

        public void setCardholderPhone(String cardholderPhone) {
            this.cardholderPhone = cardholderPhone;
        }
    }
}
