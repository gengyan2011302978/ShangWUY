package com.phjt.shangxueyuan.bean;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/6/30 18:33
 * company: 普华集团
 * description: 描述
 */
public class CourseDetailAndCatalogBean {

    public CourseDetailBean courseDetailBean;
    public List<CourseCatalogFirstBean> catalogFirstBeans;

    public CourseDetailAndCatalogBean(CourseDetailBean courseDetailBean) {
        this.courseDetailBean = courseDetailBean;
    }
    public CourseDetailAndCatalogBean(List<CourseCatalogFirstBean> courseCatalogFirstBeans) {
        this.catalogFirstBeans = courseCatalogFirstBeans;
    }
    public CourseDetailBean getCourseDetailBean() {
        return courseDetailBean;
    }

    public void setCourseDetailBean(CourseDetailBean courseDetailBean) {
        this.courseDetailBean = courseDetailBean;
    }

    public List<CourseCatalogFirstBean> getCatalogFirstBeans() {
        return catalogFirstBeans;
    }

    public void setCatalogFirstBeans(List<CourseCatalogFirstBean> catalogFirstBeans) {
        this.catalogFirstBeans = catalogFirstBeans;
    }
}
