package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseCourseListBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.CourseItemBean;
import com.phjt.shangxueyuan.bean.MineExerciseBean;
import com.phjt.shangxueyuan.mvp.contract.HotRecommendContract;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/04/27 17:21
 */

@ActivityScope
public class HotRecommendPresenter extends BasePresenter<HotRecommendContract.Model, HotRecommendContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public HotRecommendPresenter(HotRecommendContract.Model model, HotRecommendContract.View rootView) {
        super(model, rootView);
    }

    /**

     * 热门推荐列表
     * @param currentPage
     * @param pageSize
     * @param isRefresh
     */
    public void getRecommendList( int currentPage, int pageSize, boolean isRefresh) {
        mModel.getRecommendList( currentPage, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseCourseListBean<CourseItemBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseCourseListBean<CourseItemBean>> baseBean) {
                        if (baseBean.isOk()) {
                            BaseCourseListBean<CourseItemBean> baseListBean = baseBean.data;
                            if (baseListBean != null) {
                                Integer totalPage = baseListBean.getTotalPage();
                                List<CourseItemBean> recordList = baseListBean.getRecords();

                                if (isRefresh) {
                                    mRootView.showAuditionCourseRefresh(recordList);

                                    if (recordList == null || recordList.isEmpty()) {
                                        mRootView.showEmptyView();
                                    } else {
                                        mRootView.canLoadMore();
                                    }
                                } else {
                                    mRootView.showAuditionCourseLoadMore(recordList);
                                }
                                if (currentPage < totalPage) {
                                    mRootView.canLoadMore();
                                } else {
                                    mRootView.cannotLoadMore();
                                }
                            }
                        } else {
                            if (Constant.LOGOUT_CODE_ERROR != baseBean.code) {
                                mRootView.showMessage(baseBean.msg);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.showEmptyView();
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
