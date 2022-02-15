package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

/**
 * @author: gengyan
 * date:    2020/4/1 18:20
 * company: 普华集团
 * description: 课程详情
 */
public class CourseDetailBean implements Serializable {


    /**
     * id : 1
     * name : 课程名称1
     * describe : 课程描述，课程描述，课程描述
     * coverImg : 封面图333
     * collectionStatus : 1
     * freeType : 1.免费；2.会员
     * courseNum: 资料数量
     * noteNum  : 笔记数量
     * playPermission 1-无播放权限 2-有播放权限
     * videoType : 视频播放类型（1横屏播放 2竖屏播放）
     * upState : 上下架状态(0下架 1上架)
     * punchCardNum : 关联打卡数量
     */

    private String id;
    private String name;
    private String couDescribe;
    private String coverImg;
    private Integer collectionStatus;
    private String lecName;
    private Integer courseCommentNum;
    private Integer freeType;
    private Integer studyNum;
    private String couDesc;
    private String couType;
    private String buyState;
    private String sellPrice;
    private String columnId;
    private Integer punchCardNum;
    private Integer exerciseNum;

    public Integer getExerciseNum() {
        return exerciseNum;
    }

    public void setExerciseNum(Integer exerciseNum) {
        this.exerciseNum = exerciseNum;
    }

    public Integer getPunchCardNum() {
        return punchCardNum == null ? 0 : punchCardNum;
    }

    public void setPunchCardNum(Integer punchCardNum) {
        this.punchCardNum = punchCardNum;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getBuyState() {
        return buyState;
    }

    public void setBuyState(String buyState) {
        this.buyState = buyState;
    }

    public String getCouType() {
        return couType;
    }

    public void setCouType(String couType) {
        this.couType = couType;
    }

    private Integer courseNum;
    private Integer noteNum;
    private Integer playPermission;

    private Integer videoType;
    private Integer upState;

    public Integer getUpState() {
        return upState == null ? -1 : upState;
    }

    public void setUpState(Integer upState) {
        this.upState = upState;
    }

    public Integer getVideoType() {
        return videoType == null ? 0 : videoType;
    }

    public void setVideoType(Integer videoType) {
        this.videoType = videoType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCouDesc() {
        return couDesc;
    }

    public void setCouDesc(String couDesc) {
        this.couDesc = couDesc;
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

    public Integer getCollectionStatus() {
        return collectionStatus == null ? 0 : collectionStatus;
    }

    public void setCollectionStatus(Integer collectionStatus) {
        this.collectionStatus = collectionStatus;
    }

    public String getLecName() {
        return lecName;
    }

    public void setLecName(String lecName) {
        this.lecName = lecName;
    }

    public Integer getCourseCommentNum() {
        return courseCommentNum == null ? 0 : courseCommentNum;
    }

    public void setCourseCommentNum(Integer courseCommentNum) {
        this.courseCommentNum = courseCommentNum;
    }

    public Integer getFreeType() {
        return freeType == null ? 1 : freeType;
    }

    public void setFreeType(Integer freeType) {
        this.freeType = freeType;
    }

    public Integer getStudyNum() {
        return studyNum == null ? 0 : studyNum;
    }

    public void setStudyNum(Integer studyNum) {
        this.studyNum = studyNum;
    }

    public Integer getCourseNum() {
        return courseNum == null ? 0 : courseNum;
    }

    public void setCourseNum(Integer courseNum) {
        this.courseNum = courseNum;
    }

    public Integer getNoteNum() {
        return noteNum == null ? 0 : noteNum;
    }

    public void setNoteNum(Integer noteNum) {
        this.noteNum = noteNum;
    }

    public Integer getPlayPermission() {
        return playPermission == null ? 1 : playPermission;
    }

    public void setPlayPermission(Integer playPermission) {
        this.playPermission = playPermission;
    }
}

