package com.phjt.shangxueyuan.bean;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/5/7 14:29
 * company: 普华集团
 * description: 描述
 */
public class CourseCategoryBean {

    private String id;
    private String name;
    private Integer level;
    private String description;
    private String img;
    private List<CourseCategoryItemBean> childList;

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<CourseCategoryItemBean> getChildList() {
        return childList;
    }

    public void setChildList(List<CourseCategoryItemBean> childList) {
        this.childList = childList;
    }
}
