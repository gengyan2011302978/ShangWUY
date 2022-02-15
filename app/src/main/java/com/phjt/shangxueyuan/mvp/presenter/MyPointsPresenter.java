package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ShareImgBean;
import com.phjt.shangxueyuan.bean.TaskCurrencyFirstBean;
import com.phjt.shangxueyuan.bean.UserAssetsBean;
import com.phjt.shangxueyuan.mvp.contract.MyPointsContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Template
 *
 * @author :
 * description :我的积分
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 10:54
 */

@ActivityScope
public class MyPointsPresenter extends BasePresenter<MyPointsContract.Model, MyPointsContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public MyPointsPresenter(MyPointsContract.Model model, MyPointsContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 用户资产
     */
    public void getUserAssetsInfo() {
        mModel.getUserAssetsInfo()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<UserAssetsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseBean<UserAssetsBean> baseBean) {
                        if (baseBean.isOk()) {
                            if (baseBean.data != null) {
                                mRootView.showUserAssets(baseBean.data);
                            } else {
                                mRootView.showMessage("资产数据为空");
                            }
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    /**
     * 学豆任务列表
     */
    public void getNewTaskList() {
        mModel.getNewTaskList()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<TaskCurrencyFirstBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseBean<List<TaskCurrencyFirstBean>> baseBean) {
                        if (baseBean.isOk()) {
                            List<TaskCurrencyFirstBean> taskFirstBeans = baseBean.data;
                            if (taskFirstBeans != null && !taskFirstBeans.isEmpty()) {
                                mRootView.loadTaskLisSuccess(taskFirstBeans);
                            } else {
                                mRootView.showMessage("学豆列表任务为空");
                            }
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    /**
     * 请有礼接口
     */
    public void inviteShareT() {
        mModel.inviteShareT()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.isOk()) {
                            mRootView.loadInviteSharetSuccess(String.valueOf(bean.data));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     * 请有礼分享接口
     */
    public void inviteShare(String url) {
        mModel.inviteShare()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<ShareImgBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<ShareImgBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.loadInviteShareSuccess(bean, url);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mErrorHandler = null;
    }
}
