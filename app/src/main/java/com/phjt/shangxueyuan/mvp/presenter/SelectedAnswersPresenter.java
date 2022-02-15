package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.SelectedAnswersBean;
import com.phjt.shangxueyuan.bean.TutorAnsweringQuestionsBean;
import com.phjt.shangxueyuan.mvp.contract.SelectedAnswersContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;

import java.util.List;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 14:27
 * @description :
 */
@FragmentScope
public class SelectedAnswersPresenter extends BasePresenter<SelectedAnswersContract.Model, SelectedAnswersContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public SelectedAnswersPresenter(SelectedAnswersContract.Model model, SelectedAnswersContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 查询精选解答列表信息
     * int pageNo, int pageSize, String isRecommend, int timeSort, int likeSort
     */

    public void getQuestionList(int type, int currentPage, int pageSize, String isRecommend, int timeSort, int likeSorty, boolean isRefresh) {
        mModel.getQuestionList(type, currentPage, pageSize, isRecommend, timeSort, likeSorty)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<SelectedAnswersBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<SelectedAnswersBean>> baseBean) {
                        if (baseBean.isOk()) {
                            BaseListBean<SelectedAnswersBean> baseListBean = baseBean.data;
                            if (baseListBean != null) {
                                int totalPage = baseListBean.getTotalPage();
                                List<SelectedAnswersBean> recordList = baseListBean.getRecords();
                                if (isRefresh) {
                                    if (recordList != null && recordList.size() > 0) {
                                        mRootView.showWithdrawalRecordRefresh(recordList);
                                        mRootView.canLoadMore();
                                    } else {
                                        mRootView.showWithdrawalRecordRefresh(null);
                                        mRootView.showEmptyView();
                                    }
                                } else {
                                    mRootView.showWithdrawalRecordLoadMore(recordList);
                                }

                                if (currentPage < totalPage) {
                                    mRootView.canLoadMore();
                                } else {
                                    mRootView.cannotLoadMore();
                                }
                            }
                        }else {
                            mRootView.showEmptyView();
                            mRootView.stopRefreshAndLoadMore();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.showEmptyView();
                        mRootView.stopRefreshAndLoadMore();
                    }
                });
    }


    /**
     * 点赞接口
     */
    public void getCollectLike(String contentId,  int status, int position) {
        mModel.getCollectLike(contentId, status)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.isOk()) {
                            mRootView.collectLikeSuccess(position, status);
                        } else {
                            TipsUtil.show(bean.msg);
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
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}