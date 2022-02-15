package com.phjt.shangxueyuan.bean;

/**
 * @author: gengyan
 * date:    2020/3/27 11:40
 * company: 普华集团
 * description: 描述
 */
public class CourseItemBean {


    /**
     * id : 1
     * name : 课程名称1
     * couDescribe : 课程描述，22描述花时间付款后
     * coverImg : 11
     * studyNum : 0
     * freeType : 1.免费；2.会员
     */

    private String id;
    private String name;
    private String couDescribe;
    private String coverImg;
    private Integer studyNum;
    private Integer freeType;
    private String typeId;
    private String couDesc;
    private String typeName;
    private Long sumTimeLong;
    private Long courseWatchDuration;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getFreeType() {
        return freeType == null ? 0 : freeType;
    }

    public void setFreeType(Integer freeType) {
        this.freeType = freeType;
    }

    public String getCouDesc() {
        return couDesc;
    }

    public void setCouDesc(String couDesc) {
        this.couDesc = couDesc;
    }

    public Long getSumTimeLong() {
        return sumTimeLong == null ? 0 : sumTimeLong;
    }

    public void setSumTimeLong(Long sumTimeLong) {
        this.sumTimeLong = sumTimeLong;
    }

    public Long getCourseWatchDuration() {
        return courseWatchDuration == null ? 0 : courseWatchDuration;
    }

    public void setCourseWatchDuration(Long courseWatchDuration) {
        this.courseWatchDuration = courseWatchDuration;
    }
}
