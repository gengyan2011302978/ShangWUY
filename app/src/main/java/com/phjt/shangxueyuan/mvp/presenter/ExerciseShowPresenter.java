package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.ExerciseBean;
import com.phjt.shangxueyuan.bean.ExerciseShowBean;
import com.phjt.shangxueyuan.bean.MineExerciseBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.mvp.contract.ExerciseShowContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: Created by GengYan
 * date: 03/11/2021 11:13
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class ExerciseShowPresenter extends BasePresenter<ExerciseShowContract.Model, ExerciseShowContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public ExerciseShowPresenter(ExerciseShowContract.Model model, ExerciseShowContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    /**
     * 作业详情
     *
     * @param currentPage
     * @param pageSize
     * @param isRefresh
     */
    public void userAnswer(String type, String exerciseId, String couId, String trainingId, int currentPage, int pageSize, boolean isRefresh) {
        mModel.userAnswer(type, exerciseId, couId, trainingId, currentPage, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<ExerciseShowBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<ExerciseShowBean>> baseBean) {
                        if (baseBean.isOk()) {
                            BaseListBean<ExerciseShowBean> baseListBean = baseBean.data;
                            int totalPage = baseListBean.getTotalPage();
                            List<ExerciseShowBean> beanList = baseListBean.getRecords();
                            if (isRefresh) {
                                mRootView.exerciseAnswerSuccess(beanList, type);
                                mRootView.hideLoading();
                            } else {
                                mRootView.exerciseAnswerLoadMore(beanList, type);
                                mRootView.hideLoading();
                            }
                            if (currentPage < totalPage) {
                                mRootView.canLoadMore();
                            } else {
                                mRootView.cannotLoadMore();
                            }
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    /**
     * 作业详情
     */
    public void exerciseBookDetail(String id, String couId, String exerciseBookId, String trainingId) {
        mModel.exerciseBookDetail(id, couId, exerciseBookId, trainingId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<ExerciseBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<ExerciseBean> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.exerciseSuccess(baseBean);
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    public void getShareexErciseBook(String id, String couId, String trainingId) {
        mModel.getShareexErciseBook(id, couId, trainingId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<ShareItemBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<ShareItemBean> baseBean) {
                        if (baseBean.isOk()) {
                            ShareItemBean shareItemBean = baseBean.data;
                            if (shareItemBean != null) {
                                mRootView.showShareItemDialog(shareItemBean);
                            } else {
                                mRootView.showMessage("分享数据为空");
                            }
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

}
