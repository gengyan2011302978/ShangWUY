package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class ArticleClassifyBean implements Serializable {


    /**
     * id : 1
     * classifyName : 自媒体
     * classifyType : 0
     * sort : 0
     * createTime : 2020-04-29 06:31:33
     * updateTime : 2020-04-29 06:31:33
     */
    public ArticleClassifyBean(String id,String name){
        this.id = id;
        this.classifyName = name;
    }
    private String id;
    private String classifyName;
    private String classifyType;
    private int sort;
    private String createTime;
    private String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getClassifyType() {
        return classifyType;
    }

    public void setClassifyType(String classifyType) {
        this.classifyType = classifyType;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
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
}
