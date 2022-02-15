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
import com.phjt.shangxueyuan.bean.MineExerciseBean;
import com.phjt.shangxueyuan.mvp.contract.ExerciseListContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 03/22/2021 16:26
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class ExerciseListPresenter extends BasePresenter<ExerciseListContract.Model, ExerciseListContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public ExerciseListPresenter(ExerciseListContract.Model model, ExerciseListContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    /**

     * 作业列表
     * @param currentPage
     * @param pageSize
     * @param isRefresh
     */
    public void exerciseBookList(String couId,String couType, int currentPage, int pageSize, boolean isRefresh) {
        mModel.exerciseBookList(couId, couType, currentPage, pageSize)
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

}
