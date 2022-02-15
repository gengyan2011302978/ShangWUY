package com.phjt.shangxueyuan.mvp.presenter;

import android.text.TextUtils;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ExpirationNoticeBean;
import com.phjt.shangxueyuan.bean.ListBannerBean;
import com.phjt.shangxueyuan.bean.MainAnnouncementBean;
import com.phjt.shangxueyuan.bean.UpdateAppBean;
import com.phjt.shangxueyuan.mvp.contract.HomePagerContract;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TimeUtils;
import com.phsxy.utils.LogUtils;
import com.phsxy.utils.SPUtils;

import java.util.List;

import javax.inject.Inject;


/**
 * @author: Created by Template
 * date: 03/24/2020 14:09
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class HomePagerPresenter extends BasePresenter<HomePagerContract.Model, HomePagerContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public HomePagerPresenter(HomePagerContract.Model model, HomePagerContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    /**
     * 优惠券到期通知
     */
    public void  getExpirationNotice() {
        mModel.getExpirationNotice()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<ExpirationNoticeBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<ExpirationNoticeBean> bean) {
                        if (bean.isOk()) {
                            mRootView.expirationNoticeSuccess(bean.data);
                        } else {
                            LogUtils.e("========code::" + bean.code + "msg::" + bean.msg);
                        }
                    }
                });
    }

    public void getUnReadCount() {
        mModel.getUnReadCount()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<Integer>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<Integer> integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.showUnReadCount(integerBaseBean.data);
                        } else {
                            LogUtils.e("========code::" + integerBaseBean.code + "msg::" + integerBaseBean.msg);
                        }
                    }
                });
    }

    public void getBindStatus(String planetMobile,String mobile) {
        mModel.getBindStatus(planetMobile,mobile)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<Boolean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<Boolean> integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.getBindStatus(integerBaseBean.data);
                        } else {
                            LogUtils.e("========code::" + integerBaseBean.code + "msg::" + integerBaseBean.msg);
                        }
                    }
                });
    }

    public void getAnnouncementJson() {
        mModel.getAnnouncementJson()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<MainAnnouncementBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<MainAnnouncementBean> integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.showMaintenanceDialog(integerBaseBean.data);
                        } else {
                            LogUtils.e("========code::" + integerBaseBean.code + "msg::" + integerBaseBean.msg);
                        }
                    }
                });
    }

    public void getCheckVersion() {
        mModel.getCheckVersion()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<UpdateAppBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<UpdateAppBean> integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.showUpdateDialog(integerBaseBean.data);
                        } else {
                            mRootView.showGuideView();
                        }
                    }
                });
    }

    public void getActivityBannerList() {
        mModel.getActivityBanner(String.valueOf(5))
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<ListBannerBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<ListBannerBean>> listBaseBean) {
                        if (listBaseBean.isOk()) {
                            List<ListBannerBean> bannerBeanList = listBaseBean.data;
                            if (bannerBeanList != null && !bannerBeanList.isEmpty()) {
                                //一天显示一次
                                String strToday = SPUtils.getInstance().getString(Constant.SP_CURRENT_DAY);
                                if (!TextUtils.equals(TimeUtils.getStrToday(), strToday)) {
                                    SPUtils.getInstance().put(Constant.SP_CURRENT_DAY, TimeUtils.getStrToday());
                                    mRootView.showActivityBanner(bannerBeanList);
                                }
                            }
                        }
                    }
                });
    }


}
