package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ExpirationNoticeBean;
import com.phjt.shangxueyuan.bean.ListBannerBean;
import com.phjt.shangxueyuan.bean.MainAnnouncementBean;
import com.phjt.shangxueyuan.bean.UpdateAppBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: gengyan
 * date:    2020/7/14
 * company: 普华集团
 * description: 描述
 */
public interface HomePagerContract {

    interface View extends IBaseView {

        void showUnReadCount(Integer count);

        void showUpdateDialog(UpdateAppBean count);

        void showMaintenanceDialog(MainAnnouncementBean bean);

        void showGuideView();

        void showActivityBanner(List<ListBannerBean> bannerBeanList);

        /**
         * 优惠券到期通知
         */
        void expirationNoticeSuccess(ExpirationNoticeBean bean);

        void getBindStatus(boolean isBind);
    }

    interface Model extends IModel {

        Observable<BaseBean<Integer>> getUnReadCount();

        Observable<BaseBean<UpdateAppBean>> getCheckVersion();

        Observable<BaseBean<MainAnnouncementBean>> getAnnouncementJson();

        Observable<BaseBean<List<ListBannerBean>>> getActivityBanner(String bannerType);

        Observable<BaseBean> getBannerTips();

        /**
         * 优惠券到期通知
         */
        Observable<BaseBean<ExpirationNoticeBean>> getExpirationNotice();

        Observable<BaseBean<Boolean>> getBindStatus(String planetMobile, String mobile);
    }
}
