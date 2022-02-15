package com.phjt.shangxueyuan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author: gengyan
 * date:    2021/1/27 10:44
 * company: 普华集团
 * description: 训练营详情页
 */
public class TrainingDetailBean implements Serializable {


    /**
     * trainingCampName : 测试训练营
     * trainingCampDesc : 训练营介绍
     * coverImg : https://img95.699pic.com/photo/50055/5642.jpg_wh300.jpg
     * trainingCampDescribe : 营期详情介绍
     * recruitStudentStartTime : 2021-01-01 10:51
     * recruitStudentEndTime : 2021-01-27 10:53
     * curriculumStartTime : 2021-01-18 10:53
     * curriculumEndTime : 2021-02-07 10:53
     * unlockPatternStatus : 1
     * everyFew : null
     * unlockWay : null
     * unlockWayType : null
     * unlockTime : null
     * sellType : 1
     * sellPrice : 0
     * upStatus : 1
     * whetherConceal : 0
     * whetherStopSell : 0
     * applyNum : 0
     * finishNum : 0
     * trainingCampId : 1
     * joinSettingType : 1
     * labelName : 引导入群标签
     * guidanceDesc : 引导入群描述
     * codeTitle : 二维码标题
     * codePicture : https://img95.699pic.com/photo/50055/5642.jpg_wh300.jpg
     */

    private String trainingCampName;
    private String trainingCampDesc;
    private String coverImg;
    private String trainingCampDescribe;
    private String recruitStudentStartTime;
    private String recruitStudentEndTime;
    private String curriculumStartTime;
    private String curriculumEndTime;
    private String nowTime;
    private int unlockPatternStatus;
    private String everyFew;
    private String unlockWay;

    private String unlockWayType;
    private String unlockTime;
    private int sellType;
    private String sellPrice;
    private int upStatus;
    private Integer whetherConceal;
    private Integer whetherStopSell;
    private Integer applyNum;
    private Integer finishNum;
    private String id;
    private Integer joinSettingType;
    private String labelName;
    private String guidanceDesc;
    private String codeTitle;
    private String codePicture;
    private Integer number;
    private boolean buy;
    private List<String> photoList;
    private String unLockDate;
    private List<TrainingCatalogFirstBean> nodeList;
    /**
     * 资料数量，用于显示资料title使用
     */
    private int dataCount;

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public List<TrainingCatalogFirstBean> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<TrainingCatalogFirstBean> nodeList) {
        this.nodeList = nodeList;
    }

    public String getUnLockDate() {
        return unLockDate;
    }

    public void setUnLockDate(String unLockDate) {
        this.unLockDate = unLockDate;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTrainingCampName() {
        return trainingCampName;
    }

    public void setTrainingCampName(String trainingCampName) {
        this.trainingCampName = trainingCampName;
    }

    public String getTrainingCampDesc() {
        return trainingCampDesc;
    }

    public void setTrainingCampDesc(String trainingCampDesc) {
        this.trainingCampDesc = trainingCampDesc;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getTrainingCampDescribe() {
        return trainingCampDescribe;
    }

    public void setTrainingCampDescribe(String trainingCampDescribe) {
        this.trainingCampDescribe = trainingCampDescribe;
    }

    public String getRecruitStudentStartTime() {
        return recruitStudentStartTime;
    }

    public void setRecruitStudentStartTime(String recruitStudentStartTime) {
        this.recruitStudentStartTime = recruitStudentStartTime;
    }

    public String getRecruitStudentEndTime() {
        return recruitStudentEndTime;
    }

    public void setRecruitStudentEndTime(String recruitStudentEndTime) {
        this.recruitStudentEndTime = recruitStudentEndTime;
    }

    public String getCurriculumStartTime() {
        return curriculumStartTime;
    }

    public void setCurriculumStartTime(String curriculumStartTime) {
        this.curriculumStartTime = curriculumStartTime;
    }

    public String getCurriculumEndTime() {
        return curriculumEndTime;
    }

    public void setCurriculumEndTime(String curriculumEndTime) {
        this.curriculumEndTime = curriculumEndTime;
    }

    public int getUnlockPatternStatus() {
        return unlockPatternStatus;
    }

    public void setUnlockPatternStatus(int unlockPatternStatus) {
        this.unlockPatternStatus = unlockPatternStatus;
    }

    public String getEveryFew() {
        return everyFew;
    }

    public void setEveryFew(String everyFew) {
        this.everyFew = everyFew;
    }

    public String getUnlockWay() {
        return unlockWay;
    }

    public void setUnlockWay(String unlockWay) {
        this.unlockWay = unlockWay;
    }

    public String getUnlockWayType() {
        return unlockWayType;
    }

    public void setUnlockWayType(String unlockWayType) {
        this.unlockWayType = unlockWayType;
    }

    public String getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(String unlockTime) {
        this.unlockTime = unlockTime;
    }

    public int getSellType() {
        return sellType;
    }

    public void setSellType(int sellType) {
        this.sellType = sellType;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getUpStatus() {
        return upStatus;
    }

    public void setUpStatus(int upStatus) {
        this.upStatus = upStatus;
    }

    public Integer getWhetherConceal() {
        return whetherConceal == null ? 0 : whetherConceal;
    }

    public void setWhetherConceal(Integer whetherConceal) {
        this.whetherConceal = whetherConceal;
    }

    public Integer getWhetherStopSell() {
        return whetherStopSell == null ? 0 : whetherStopSell;
    }

    public void setWhetherStopSell(Integer whetherStopSell) {
        this.whetherStopSell = whetherStopSell;
    }

    public Integer getApplyNum() {
        return applyNum == null ? 0 : applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public Integer getFinishNum() {
        return finishNum == null ? 0 : finishNum;
    }

    public void setFinishNum(Integer finishNum) {
        this.finishNum = finishNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getJoinSettingType() {
        return joinSettingType == null ? 0 : joinSettingType;
    }

    public void setJoinSettingType(Integer joinSettingType) {
        this.joinSettingType = joinSettingType;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getGuidanceDesc() {
        return guidanceDesc;
    }

    public void setGuidanceDesc(String guidanceDesc) {
        this.guidanceDesc = guidanceDesc;
    }

    public String getCodeTitle() {
        return codeTitle;
    }

    public void setCodeTitle(String codeTitle) {
        this.codeTitle = codeTitle;
    }

    public String getCodePicture() {
        return codePicture;
    }

    public void setCodePicture(String codePicture) {
        this.codePicture = codePicture;
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }
}
