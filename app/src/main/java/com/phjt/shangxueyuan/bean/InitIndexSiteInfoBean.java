package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class InitIndexSiteInfoBean implements Serializable {


    /**
     * id : 1
     * parentId : null
     * topId : null
     * name : 精选试听
     * level : null
     * type : 2
     * state : null
     * description : null
     * researchChannelDesc : null
     * channel : 1
     * freeType : null
     * sort : null
     * createTime : null
     * updateTime : null
     */

    private String id;
    private Object parentId;
    private Object topId;
    private String name;
    private Object level;
    private int type;
    private Object state;
    private Object description;
    private Object researchChannelDesc;
    private int channel;
    private Object freeType;
    private Object sort;
    private Object createTime;
    private Object updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public Object getTopId() {
        return topId;
    }

    public void setTopId(Object topId) {
        this.topId = topId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getLevel() {
        return level;
    }

    public void setLevel(Object level) {
        this.level = level;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getResearchChannelDesc() {
        return researchChannelDesc;
    }

    public void setResearchChannelDesc(Object researchChannelDesc) {
        this.researchChannelDesc = researchChannelDesc;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public Object getFreeType() {
        return freeType;
    }

    public void setFreeType(Object freeType) {
        this.freeType = freeType;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }
}
