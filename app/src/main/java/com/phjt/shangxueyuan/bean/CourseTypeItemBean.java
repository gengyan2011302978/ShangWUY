package com.phjt.shangxueyuan.bean;

/**
 * @author: gengyan
 * date:    2020/4/1 11:56
 * company: 普华集团
 * description: 描述
 */
public class CourseTypeItemBean {

    private String id;
    private String name;
    private boolean isSelect;

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

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public CourseTypeItemBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public CourseTypeItemBean() {
    }
}
