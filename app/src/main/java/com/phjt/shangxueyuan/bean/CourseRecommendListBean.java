package com.phjt.shangxueyuan.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class CourseRecommendListBean implements Serializable {

    /**
     * id : 3
     * typeId : 分类
     * coursewareName : 二级分类名称
     * name : 33课程
     * coverImg : 33
     * couDesc : 课程简介
     * studyNum : 100
     * freeType : 是否免费：1.免费；2.会员；
     */

    private String id;
    private String typeId;
    private String coursewareName;
    private String name;
    private String coverImg;
    private String couDesc;
    private String studyNum;
    private String freeType;
    private String typeName;
    private String classifyName;

    /**
     * 类型：9直播回放 5商科直通车 10 数字经济 8财富文化提升
     */
    private int type;

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getCoursewareName() {
        return coursewareName;
    }

    public void setCoursewareName(String coursewareName) {
        this.coursewareName = coursewareName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getCouDesc() {
        return couDesc;
    }

    public void setCouDesc(String couDesc) {
        this.couDesc = couDesc;
    }

    public String getStudyNum() {
        return studyNum;
    }

    public void setStudyNum(String studyNum) {
        this.studyNum = studyNum;
    }

    public String getFreeType() {
        return freeType;
    }

    public void setFreeType(String freeType) {
        this.freeType = freeType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
