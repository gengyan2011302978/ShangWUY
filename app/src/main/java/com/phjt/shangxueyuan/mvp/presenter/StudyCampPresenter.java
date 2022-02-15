package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MineExerciseBean;
import com.phjt.shangxueyuan.bean.StudyCampBean;
import com.phjt.shangxueyuan.mvp.contract.StudyCampContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/04/23 09:41
 */

@ActivityScope
public class StudyCampPresenter extends BasePresenter<StudyCampContract.Model, StudyCampContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public StudyCampPresenter(StudyCampContract.Model model, StudyCampContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 成长营列表
     *
     * @param currentPage
     * @param pageSize
     * @param isRefresh
     */
    public void getStudyCampList(String trainingCampType,int currentPage, int pageSize, boolean isRefresh) {
        mModel.getStudyCampList(trainingCampType,currentPage, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<StudyCampBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<StudyCampBean>> baseBean) {
                        if (baseBean.isOk()) {
                            BaseListBean<StudyCampBean> baseListBean = baseBean.data;
                            if (baseListBean != null) {
                                int totalPage = baseListBean.getTotalPage();
                                List<StudyCampBean> commentList = baseListBean.getRecords();

                                if (isRefresh) {
                                    if (commentList != null && !commentList.isEmpty()) {
                                        mRootView.showStudyCampRefresh(commentList);
                                        mRootView.canLoadMore();
                                    } else {
                                        mRootView.showStudyCampRefresh(null);
                                        mRootView.showEmptyView();
                                    }
                                } else {
                                    mRootView.showStudyCampLoadMore(commentList);
                                }

                                if (currentPage < totalPage) {
                                    mRootView.canLoadMore();
                                } else {
                                    mRootView.cannotLoadMore();
                                }
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
        this.mAppManager = null;
        this.mErrorHandler = null;
    }
}
