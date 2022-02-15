package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MesExerciseBean;
import com.phjt.shangxueyuan.bean.MessageListBean;
import com.phjt.shangxueyuan.bean.ShareImgBean;
import com.phjt.shangxueyuan.mvp.contract.InteractionMessageContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/20 13:54
 */

@ActivityScope
public class InteractionMessagePresenter extends BasePresenter<InteractionMessageContract.Model, InteractionMessageContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public InteractionMessagePresenter(InteractionMessageContract.Model model, InteractionMessageContract.View rootView) {
        super(model, rootView);
    }

    public void getListMessage(int type, int pageNo, int pageSize, boolean isRefresh) {
        mModel.getListMessage(type, pageNo, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<MessageListBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<MessageListBean> bean) {
                        if (bean.isOk()) {
                            if (isRefresh) {
                                mRootView.getRefresh(bean.data.getRecords());
                                mRootView.canLoadMore();
                            } else {
                                mRootView.getLoadMore(bean.data.getRecords());
                            }
                            if (pageNo >= bean.data.getTotalPage()) {
                                mRootView.cannotLoadMore();
                            } else {
                                mRootView.canLoadMore();
                            }
                        } else {
                            mRootView.getLoadFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.getLoadFail();
                    }
                });
    }

    /**
     * 请有礼分享接口
     */
    public void inviteShare() {
        mModel.inviteShare()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<ShareImgBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<ShareImgBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.loadInviteShareSuccess(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     * 请有礼接口
     */
    public void inviteCourtesy() {
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
     * 获取消息跳转作业详情
     */
    public void getMesExerciseDetail(String otherId) {
        mModel.getMesExerciseDetail(otherId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<MesExerciseBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<MesExerciseBean> bean) {
                        if (bean.isOk()) {
                            mRootView.loadMesExerciseSuccess(bean.data);
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
