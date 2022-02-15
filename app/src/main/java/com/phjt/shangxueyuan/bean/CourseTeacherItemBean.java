package com.phjt.shangxueyuan.bean;

/**
 * @author: gengyan
 * date:    2020/4/1 13:08
 * company: 普华集团
 * description: 描述
 */
public class CourseTeacherItemBean {

    private String id;
    private String name;
    private boolean isSelect;

    public CourseTeacherItemBean() {
    }

    public CourseTeacherItemBean(String name) {
        this.name = name;
    }

    public CourseTeacherItemBean(String name, boolean isSelect) {
        this.name = name;
        this.isSelect = isSelect;
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

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
