package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CourseCategoryBean;
import com.phjt.shangxueyuan.bean.OnDemandBean;
import com.phjt.shangxueyuan.bean.SuspendImgBean;
import com.phjt.shangxueyuan.mvp.contract.CourseCategoryContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phsxy.utils.NetworkUtils;


/**
 * @author: Created by GengYan
 * date: 05/06/2020 13:55
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class CourseCategoryPresenter extends BasePresenter<CourseCategoryContract.Model, CourseCategoryContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public CourseCategoryPresenter(CourseCategoryContract.Model model, CourseCategoryContract.View rootView) {
        super(model, rootView);
    }

    public void getCourseCategory(String id) {

        mRootView.isShowNoNetworkView(!NetworkUtils.isConnected());

        mModel.getCourseCategory(id)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<CourseCategoryBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<CourseCategoryBean> baseBean) {
                        if (baseBean.isOk()) {
                            CourseCategoryBean categoryBean = baseBean.data;
                            if (categoryBean != null) {
                                mRootView.getCourseCategory(categoryBean);
                            }
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    public void getOnDemandBean() {
        mModel.getOnDemandBean()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<OnDemandBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<OnDemandBean> baseBean) {
                        if (baseBean.isOk()) {
                            OnDemandBean onDemandBean = baseBean.data;
                            if (onDemandBean != null) {
                                mRootView.showOnDemandInfo(onDemandBean);
                            }
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    /**
     * 课程列表浮层
     */
    public void getSuspendImg() {
        mModel.getSuspendImg()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<SuspendImgBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<SuspendImgBean> baseBean) {
                        if (baseBean.isOk()) {
                            SuspendImgBean mBean = baseBean.data;
                            if (mBean != null) {
                                mRootView.showSuspendImgInfo(mBean);
                            }
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}
