package com.phjt.shangxueyuan.bean;

/**
 * @author: Roy
 * date:   2021/1/15
 * company: 普华集团
 * description:打卡列表的实体类
 */
public class ParticipatingPunchBean {

    private String id;
    private String coverImg;
    private String punchCardName;
    private String focusDescribe;
    private String punchCardStartDate;
    private String couId;
    private String diaryId;
    private Integer punchNum;
    private Integer totalNum;
    private int punchStatus;
    private Integer days;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getPunchCardName() {
        return punchCardName;
    }

    public void setPunchCardName(String punchCardName) {
        this.punchCardName = punchCardName;
    }

    public String getFocusDescribe() {
        return focusDescribe;
    }

    public void setFocusDescribe(String focusDescribe) {
        this.focusDescribe = focusDescribe;
    }

    public String getPunchCardStartDate() {
        return punchCardStartDate;
    }

    public void setPunchCardStartDate(String punchCardStartDate) {
        this.punchCardStartDate = punchCardStartDate;
    }

    public String getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(String diaryId) {
        this.diaryId = diaryId;
    }

    public String getCouId() {
        return couId;
    }

    public void setCouId(String couId) {
        this.couId = couId;
    }

    public Integer getPunchNum() {
        return punchNum == null ? 0 : punchNum;
    }

    public void setPunchNum(Integer punchNum) {
        this.punchNum = punchNum;
    }

    public Integer getTotalNum() {
        return totalNum == null ? 0 : totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public int getPunchStatus() {
        return punchStatus;
    }

    public void setPunchStatus(int punchStatus) {
        this.punchStatus = punchStatus;
    }

    public Integer getDays() {
        return days == null ? 0 : days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
