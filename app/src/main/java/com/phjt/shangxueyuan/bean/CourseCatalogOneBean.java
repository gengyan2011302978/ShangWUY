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
public class CourseCatalogOneBean extends AbstractExpandableItem<CourseCatalogSecondBean> implements MultiItemEntity {

    private String couId;
    private String name;
    private String couDescribe;

    public String getCouDescribe() {
        return couDescribe;
    }

    public void setCouDescribe(String couDescribe) {
        this.couDescribe = couDescribe;
    }

    public String getCouId() {
        return couId;
    }

    public void setCouId(String couId) {
        this.couId = couId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List<CouWareVOSBean> couWareVOS;

    public List<CouWareVOSBean> getCouWareVOS() {
        return couWareVOS;
    }

    public void setCouWareVOS(List<CouWareVOSBean> couWareVOS) {
        this.couWareVOS = couWareVOS;
    }

    public static class CouWareVOSBean {
        /**
         * coursewareId : 17
         * coursewareName : 2
         * couPointVOs : [{"pointId":63,"pointIdName":"没过脑子","videoId":"5285890800995189257","videoUrl":"http://1300105446.vod2.myqcloud.co","pointWatchDuration":null,"watchTime":null,"isLastWatch":null}]
         */

        private List<CourseCatalogSecondBean> couPointVOs;
        private String coursewareId;
        private String coursewareName;
        public List<CourseCatalogSecondBean> getCouPointVOs() {
            return couPointVOs;
        }

        public void setCouPointVOs(List<CourseCatalogSecondBean> couPointVOs) {
            this.couPointVOs = couPointVOs;
        }
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
