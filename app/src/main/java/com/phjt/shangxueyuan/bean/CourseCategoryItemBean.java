package com.phjt.shangxueyuan.bean;

/**
 * @author: gengyan
 * date:    2020/5/7 14:29
 * company: 普华集团
 * description: 描述
 */
public class CourseCategoryItemBean {

    /**
     * id : 0
     * name :
     * level : 2
     * description :
     * img :
     */

    private String id;
    private String name;
    private Integer level;
    private String description;
    private String img;

    public CourseCategoryItemBean() {
    }

    public CourseCategoryItemBean(String id, String name, Integer level) {
        this.id = id;
        this.name = name;
        this.level = level;
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
}
