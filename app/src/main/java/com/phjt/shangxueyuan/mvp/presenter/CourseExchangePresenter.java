package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.ExchangeCodeBean;
import com.phjt.shangxueyuan.bean.MineExerciseBean;
import com.phjt.shangxueyuan.bean.MyNotesBean;
import com.phjt.shangxueyuan.bean.TrainingBattalionExchangeBean;
import com.phjt.shangxueyuan.mvp.contract.CourseExchangeContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 08/06/2020 09:48
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class CourseExchangePresenter extends BasePresenter<CourseExchangeContract.Model, CourseExchangeContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public CourseExchangePresenter(CourseExchangeContract.Model model, CourseExchangeContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    public void getRefreshList(int pageNo, int pageSize, boolean isRefresh) {
        mModel.getCourseExchangeRecord(pageNo, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<ExchangeCodeBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<ExchangeCodeBean> bean) {
                        if (bean.isOk()) {
                            mRootView.loadRefreshDataSuccess(bean.data, pageNo,isRefresh);
                        } else {
                            mRootView.loadRefreshDataFailure();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadRefreshDataFailure();
                    }
                });
    }

    /**
     * 训练营兑换记录接口
     * @param pageNo
     * @param pageSize
     * @param isRefresh
     */
    public void getRecordList(int pageNo, int pageSize, boolean isRefresh) {
        mModel.getRecordList(pageNo, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<TrainingBattalionExchangeBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<TrainingBattalionExchangeBean>> baseBean) {
                        if (baseBean.isOk()) {
                            BaseListBean<TrainingBattalionExchangeBean> baseListBean = baseBean.data;
                            if (baseListBean != null) {
                                int totalPage = baseListBean.getTotalPage();
                                List<TrainingBattalionExchangeBean> commentList = baseListBean.getRecords();

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

                                if (pageNo < totalPage) {
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


}
