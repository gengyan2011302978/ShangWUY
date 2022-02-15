package com.phjt.shangxueyuan.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

public class ThemeBean implements Serializable {


    /**
     * id : 1
     * punchCardStartDate : 2021-01-11
     * punchCardEndDate : 2021-01-20
     * punchCardStartTime : 00:00:00
     * punchCardEndTime : 23:59:59
     * punchCardCondition : 100
     * partyNum : null
     * reissueCardType : 0
     * reissueCardNum : null
     * reissueCardUseNum : 0
     * nowDate : 2021-01-21 13:45:19
     * recordVos : [{"createTime":"2021-01-11 16:06:06","userId":null,"partyNum":2,"calendarDate":"2021-01-13 "},{"createTime":"2021-01-13 16:06:14","userId":null,"partyNum":2,"calendarDate":"2021-01-13 "},{"createTime":"2021-01-19 10:50:14","userId":null,"partyNum":2,"calendarDate":"2021-01-13 "}]
     * motifs : [{"id":1,"punchCardId":1,"motifTitle":"昨天主题","motifDate":"2021-01-17 13:44:07","coverImg":null,"createTime":"2021-01-18 13:44:23","updateTime":"2021-01-18 13:44:23","motifDescribe":"11"},{"id":2,"punchCardId":1,"motifTitle":"今日主题","motifDate":"2021-01-18 13:44:40","coverImg":null,"createTime":"2021-01-18 13:44:45","updateTime":"2021-01-18 13:44:45","motifDescribe":"22"},{"id":3,"punchCardId":1,"motifTitle":"明日主题","motifDate":"2021-01-19 13:44:54","coverImg":null,"createTime":"2021-01-18 13:44:59","updateTime":"2021-01-18 13:44:59","motifDescribe":"33"}]
     */

    private int id;
    private String punchCardStartDate;
    private String punchCardEndDate;
    private String punchCardStartTime;
    private String punchCardEndTime;
    private int punchCardCondition;
    private String partyNum;
    private int reissueCardType;
    private String reissueCardNum;
    private int reissueCardUseNum;
    private String nowDate;
    private List<RecordVosBean> recordVos;
    private List<MotifsBean> motifs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPunchCardStartTime() {
        return punchCardStartTime;
    }

    public void setPunchCardStartTime(String punchCardStartTime) {
        this.punchCardStartTime = punchCardStartTime;
    }

    public String getPunchCardEndTime() {
        return punchCardEndTime;
    }

    public void setPunchCardEndTime(String punchCardEndTime) {
        this.punchCardEndTime = punchCardEndTime;
    }

    public int getPunchCardCondition() {
        return punchCardCondition;
    }

    public void setPunchCardCondition(int punchCardCondition) {
        this.punchCardCondition = punchCardCondition;
    }

    public String getPartyNum() {
        return partyNum;
    }

    public void setPartyNum(String partyNum) {
        this.partyNum = partyNum;
    }

    public int getReissueCardType() {
        return reissueCardType;
    }

    public void setReissueCardType(int reissueCardType) {
        this.reissueCardType = reissueCardType;
    }

    public String getReissueCardNum() {
        if (TextUtils.isEmpty(reissueCardNum)) {
            return "0";
        } else {
            return String.valueOf(Integer.parseInt(reissueCardNum) - reissueCardUseNum);
        }
    }

    public void setReissueCardNum(String reissueCardNum) {
        this.reissueCardNum = reissueCardNum;
    }

    public int getReissueCardUseNum() {
        return reissueCardUseNum;
    }

    public void setReissueCardUseNum(int reissueCardUseNum) {
        this.reissueCardUseNum = reissueCardUseNum;
    }

    public String getNowDate() {
        return nowDate;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }

    public List<RecordVosBean> getRecordVos() {
        return recordVos;
    }

    public void setRecordVos(List<RecordVosBean> recordVos) {
        this.recordVos = recordVos;
    }

    public List<MotifsBean> getMotifs() {
        return motifs;
    }

    public void setMotifs(List<MotifsBean> motifs) {
        this.motifs = motifs;
    }

    public static class RecordVosBean {
        /**
         * createTime : 2021-01-11 16:06:06
         * userId : null
         * partyNum : 2
         * calendarDate : 2021-01-13
         */

        private String createTime;
        private String userId;
        private int partyNum;
        private String calendarDate;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getPartyNum() {
            return partyNum;
        }

        public void setPartyNum(int partyNum) {
            this.partyNum = partyNum;
        }

        public String getCalendarDate() {
            return calendarDate;
        }

        public void setCalendarDate(String calendarDate) {
            this.calendarDate = calendarDate;
        }
    }

    public static class MotifsBean {
        /**
         * id : 1
         * punchCardId : 1
         * motifTitle : 昨天主题
         * motifDate : 2021-01-17 13:44:07
         * coverImg : null
         * createTime : 2021-01-18 13:44:23
         * updateTime : 2021-01-18 13:44:23
         * motifDescribe : 11
         */

        private int id;
        private int punchCardId;
        private String motifTitle;
        private String motifDate;
        private String coverImg;
        private String createTime;
        private String updateTime;
        private String motifDescribe;

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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getMotifDescribe() {
            return motifDescribe;
        }

        public void setMotifDescribe(String motifDescribe) {
            this.motifDescribe = motifDescribe;
        }
    }
}
