package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class MotifDetailBean implements Serializable {


    /**
     * id : 2
     * punchCardId : 1
     * motifTitle : 今日主题
     * motifDate : 2021-01-18
     * coverImg : null
     * motifDescribe : 22
     * punchCardState : 0
     * nowDate : 2021-01-27 14:15:10
     */

    private int id;
    private int punchCardId;
    private String motifTitle;
    private String motifDate;
    private String coverImg;
    private String motifDescribe;
    private int punchCardState;
    private String nowDate;
    private String reissueCardType;
    private String punchCardEndTime;
    private String punchCardStartTime;
    private String punchCardStartDate;
    private String punchCardEndDate;
    private String reissueCardNum = "0";
    private String reissueCardUseNum = "0";
    private String otherId;
    private String punchCardType;

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    public String getPunchCardType() {
        if (null==punchCardType){
            return "3";
        }
        return punchCardType;
    }

    public void setPunchCardType(String punchCardType) {
        this.punchCardType = punchCardType;
    }

    public String getReissueCardNum() {
        return String.valueOf(Integer.parseInt(reissueCardNum)-Integer.parseInt(reissueCardUseNum));
    }

    public void setReissueCardNum(String reissueCardNum) {
        this.reissueCardNum = reissueCardNum;
    }

    public String getReissueCardUseNum() {
        return reissueCardUseNum;
    }

    public void setReissueCardUseNum(String reissueCardUseNum) {
        this.reissueCardUseNum = reissueCardUseNum;
    }

    public String getPunchCardEndTime() {
        return punchCardEndTime;
    }

    public void setPunchCardEndTime(String punchCardEndTime) {
        this.punchCardEndTime = punchCardEndTime;
    }

    public String getPunchCardStartTime() {
        return punchCardStartTime;
    }

    public void setPunchCardStartTime(String punchCardStartTime) {
        this.punchCardStartTime = punchCardStartTime;
    }

    public String getPunchCardStartDate() {
        return punchCardStartDate;
    }

    public void setPunchCardStartDate(String punchCardStartDate) {
        this.punchCardStartDate = punchCardStartDate;
    }

    public String getPunchCardEndDate() {
        return punchCardEndDate;
    }

    public void setPunchCardEndDate(String punchCardEndDate) {
        this.punchCardEndDate = punchCardEndDate;
    }

    public String getReissueCardType() {
        return reissueCardType;
    }

    public void setReissueCardType(String reissueCardType) {
        this.reissueCardType = reissueCardType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPunchCardId() {
        return punchCardId;
    }

    public void setPunchCardId(int punchCardId) {
        this.punchCardId = punchCardId;
    }

    public String getMotifTitle() {
        return motifTitle;
    }

    public void setMotifTitle(String motifTitle) {
        this.motifTitle = motifTitle;
    }

    public String getMotifDate() {
        return motifDate;
    }

    public void setMotifDate(String motifDate) {
        this.motifDate = motifDate;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getMotifDescribe() {
        return motifDescribe;
    }

    public void setMotifDescribe(String motifDescribe) {
        this.motifDescribe = motifDescribe;
    }

    public int getPunchCardState() {
        return punchCardState;
    }

    public void setPunchCardState(int punchCardState) {
        this.punchCardState = punchCardState;
    }

    public String getNowDate() {
        return nowDate;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }
}
