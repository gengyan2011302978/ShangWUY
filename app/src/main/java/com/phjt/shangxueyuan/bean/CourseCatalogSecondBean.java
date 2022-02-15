package com.phjt.shangxueyuan.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.phjt.shangxueyuan.mvp.ui.adapter.CourseCatalogAdapter;

/**
 * @author: gengyan
 * date:    2020/3/27 18:54
 * company: 普华集团
 * description: 描述
 */
public class CourseCatalogSecondBean implements MultiItemEntity {


    /**
     * pointId : 1
     * pointIdName : 小节名称视频1
     * videoId : 1
     * videoUrl : url1111
     * wareId: 章id
     * downState : 下载状态
     */

    private String pointId;
    private String pointIdName;
    private String videoId;
    private String videoUrl;
    private boolean isSelect;
    private Long pointWatchDuration;
    private Integer isLastWatch;
    private String wareId;
    private String title;
    private String courseId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private int downState;

    public int getDownState() {
        return downState;
    }

    public void setDownState(int downState) {
        this.downState = downState;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getPointIdName() {
        return pointIdName;
    }

    public void setPointIdName(String pointIdName) {
        this.pointIdName = pointIdName;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public Long getPointWatchDuration() {
        return pointWatchDuration == null ? 0 : pointWatchDuration;
    }

    public void setPointWatchDuration(Long pointWatchDuration) {
        this.pointWatchDuration = pointWatchDuration;
    }

    public Integer getIsLastWatch() {
        return isLastWatch == null ? 0 : isLastWatch;
    }

    public void setIsLastWatch(Integer isLastWatch) {
        this.isLastWatch = isLastWatch;
    }

    public String getWareId() {
        return wareId;
    }

    public void setWareId(String wareId) {
        this.wareId = wareId;
    }



    @Override
    public int getItemType() {
        return CourseCatalogAdapter.TYPE_LEVEL_1;
    }
}
