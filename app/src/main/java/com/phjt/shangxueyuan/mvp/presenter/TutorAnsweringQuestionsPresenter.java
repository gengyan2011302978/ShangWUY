package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.TutorAnsweringQuestionsBean;
import com.phjt.shangxueyuan.mvp.contract.TutorAnsweringQuestionsContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 14:21
 * @description :
 */
@FragmentScope
public class TutorAnsweringQuestionsPresenter extends BasePresenter<TutorAnsweringQuestionsContract.Model, TutorAnsweringQuestionsContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public TutorAnsweringQuestionsPresenter(TutorAnsweringQuestionsContract.Model model, TutorAnsweringQuestionsContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 获取导师信息列表
     */
    public void getTutorInfoList(int currentPage, int pageSize, String realmId, int mType, boolean isRefresh) {
        mModel.getTutorInfoList(currentPage, pageSize, realmId, mType)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<TutorAnsweringQuestionsBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<TutorAnsweringQuestionsBean>> baseBean) {
                        if (baseBean.isOk()) {
                            BaseListBean<TutorAnsweringQuestionsBean> baseListBean = baseBean.data;
                            if (baseListBean != null) {
                                int totalPage = baseListBean.getTotalPage();
                                List<TutorAnsweringQuestionsBean> commentList = baseListBean.getRecords();
                                if (isRefresh) {
                                    if (commentList != null && !commentList.isEmpty()) {
                                        mRootView.showWithdrawalRecordRefresh(commentList);
                                        mRootView.canLoadMore();
                                    } else {
                                        mRootView.showWithdrawalRecordRefresh(null);
                                        mRootView.showEmptyView();
                                    }
                                } else {
                                    mRootView.showWithdrawalRecordLoadMore(commentList);

                                }
                                if (currentPage < totalPage) {
                                    mRootView.canLoadMore();
                                } else {
                                    mRootView.cannotLoadMore();
                                }
                            }
                        } else {
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
     * 咨询-我的咨询
     *
     * @param currentPage
     * @param pageSize
     * @param isRecommend
     * @param timeSort
     * @param likeSorty
     * @param isRefresh
     */
    public void getMyConsultationList(int currentPage, int pageSize, String isRecommend, int timeSort, int likeSorty, boolean isRefresh) {
        mModel.getMyConsultationList(currentPage, pageSize, isRecommend, timeSort, likeSorty)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<TutorAnsweringQuestionsBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<TutorAnsweringQuestionsBean>> baseBean) {
                        if (baseBean.isOk()) {
                            BaseListBean<TutorAnsweringQuestionsBean> baseListBean = baseBean.data;
                            if (baseListBean != null) {
                                int totalPage = baseListBean.getTotalPage();
                                List<TutorAnsweringQuestionsBean> recordList = baseListBean.getRecords();
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
                        } else {
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
     * 校验用户学分是否足够支付提问
     */

    public void checkUserCapital(String mTutorId, TutorAnsweringQuestionsBean bean) {
        mModel.checkUserCapital(mTutorId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.checkUserCapitalSuccess(bean);
                        } else if (bean.code == 201) {
                            mRootView.checkUserCapitalFail(bean.getQuestionCoin());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
//                        mRootView.checkUserCapitalFail();
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