package com.phjt.shangxueyuan.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.phjt.shangxueyuan.mvp.ui.adapter.CourseCatalogAdapter;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/3/27 18:53
 * company: 普华集团
 * description: 描述
 */
public class CourseCatalogFirstBean extends AbstractExpandableItem<CourseCatalogSecondBean> implements MultiItemEntity {

    private String coursewareId;
    private String coursewareName;
    private String couDescribe;

    public String getCouDescribe() {
        return couDescribe;
    }

    public void setCouDescribe(String couDescribe) {
        this.couDescribe = couDescribe;
    }

    private List<CourseCatalogSecondBean> couPointVOs;

    public String getCoursewareId() {
        return coursewareId;
    }

    public void setCoursewareId(String coursewareId) {
        this.coursewareId = coursewareId;
    }

    public String getCoursewareName() {
        return coursewareName;
    }

    public void setCoursewareName(String coursewareName) {
        this.coursewareName = coursewareName;
    }

    public List<CourseCatalogSecondBean> getCouPointVOs() {
        return couPointVOs;
    }

    public void setCouPointVOs(List<CourseCatalogSecondBean> couPointVOs) {
        this.couPointVOs = couPointVOs;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return CourseCatalogAdapter.TYPE_LEVEL_0;
    }
}
