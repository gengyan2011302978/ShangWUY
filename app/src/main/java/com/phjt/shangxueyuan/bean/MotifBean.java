package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class MotifBean implements Serializable {


    /**
     * id : 2
     * punchCardId : 1
     * motifTitle : 今日主题
     * motifDate : 2021-01-18
     * coverImg : null
     * motifDescribe : 22
     */

    private int id;
    private int punchCardId;
    private String motifTitle;
    private String motifDate;
    private String coverImg;
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

    public String getMotifDescribe() {
        return motifDescribe;
    }

    public void setMotifDescribe(String motifDescribe) {
        this.motifDescribe = motifDescribe;
    }
}
