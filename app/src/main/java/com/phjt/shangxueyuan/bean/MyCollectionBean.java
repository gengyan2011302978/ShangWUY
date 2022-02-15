package com.phjt.shangxueyuan.bean;

/**
 * @author: Roy
 * date:   2020/3/30
 * company: 普华集团
 * description:
 */
public class MyCollectionBean {

    private String name;
    private String couDescribe;
    private String coverImg;
    private Integer studyNum;
    private String createTime;
    private int id;
    private int freeType;
    private int couId;
    private int delStatus;
    public boolean isCheck;
    public int upState;
    private String specialTitle;
    private String specialContent;
    private Integer collectNum;
    private String specialId;

    public String getSpecialId() {
        return specialId;
    }

    public void setSpecialId(String specialId) {
        this.specialId = specialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCouDescribe() {
        return couDescribe;
    }

    public void setCouDescribe(String couDescribe) {
        this.couDescribe = couDescribe;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public Integer getStudyNum() {
        return studyNum == null ? 0 : studyNum;
    }

    public void setStudyNum(Integer studyNum) {
        this.studyNum = studyNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCouId() {
        return couId;
    }

    public void setCouId(int couId) {
        this.couId = couId;
    }

    public int getFreeType() {
        return freeType;
    }

    public void setFreeType(int freeType) {
        this.freeType = freeType;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    public int getUpState() {
        return upState;
    }

    public void setUpState(int upState) {
        this.upState = upState;
    }

    public String getSpecialTitle() {
        return specialTitle;
    }

    public void setSpecialTitle(String specialTitle) {
        this.specialTitle = specialTitle;
    }

    public String getSpecialContent() {
        return specialContent;
    }

    public void setSpecialContent(String specialContent) {
        this.specialContent = specialContent;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }
}
