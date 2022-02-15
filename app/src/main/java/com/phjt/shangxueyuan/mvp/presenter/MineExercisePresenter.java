package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.CourseCommentBean;
import com.phjt.shangxueyuan.bean.MineExerciseBean;
import com.phjt.shangxueyuan.mvp.contract.MineExerciseContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/03/17 10:24
 */

@FragmentScope
public class MineExercisePresenter extends BasePresenter<MineExerciseContract.Model, MineExerciseContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public MineExercisePresenter(MineExerciseContract.Model model, MineExerciseContract.View rootView) {
        super(model, rootView);
    }

    /**

     * 我的作业列表
     * @param commentType
     * @param currentPage
     * @param pageSize
     * @param isRefresh
     */
    public void myExerciseBookList(String commentType, int currentPage, int pageSize, boolean isRefresh) {
        mModel.myExerciseBookList(commentType, currentPage, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<MineExerciseBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<MineExerciseBean>> baseBean) {
                        if (baseBean.isOk()) {
                            BaseListBean<MineExerciseBean> baseListBean = baseBean.data;
                            if (baseListBean != null) {
                                int totalPage = baseListBean.getTotalPage();
                                List<MineExerciseBean> commentList = baseListBean.getRecords();

                                if (isRefresh) {
                                    if (commentList != null && !commentList.isEmpty()) {
                                        mRootView.showCommentListRefresh(commentList);
                                        mRootView.canLoadMore();
                                    } else {
                                        mRootView.showCommentListRefresh(null);
                                        mRootView.showEmptyView();
                                    }
                                } else {
                                    mRootView.showCommentListLoadMore(commentList);
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
