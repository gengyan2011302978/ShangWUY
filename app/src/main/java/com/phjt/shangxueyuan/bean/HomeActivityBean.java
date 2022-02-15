package com.phjt.shangxueyuan.bean;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/7/16 10:27
 * company: 普华集团
 * description: 首页活动实体
 */
public class HomeActivityBean {

    private boolean isTip;

    private List<ListBannerBean> bannerBeans;

    public HomeActivityBean(boolean isTip, List<ListBannerBean> bannerBeans) {
        this.isTip = isTip;
        this.bannerBeans = bannerBeans;
    }

    public boolean isTip() {
        return isTip;
    }

    public void setTip(boolean tip) {
        isTip = tip;
    }

    public List<ListBannerBean> getBannerBeans() {
        return bannerBeans;
    }

    public void setBannerBeans(List<ListBannerBean> bannerBeans) {
        this.bannerBeans = bannerBeans;
    }
}
