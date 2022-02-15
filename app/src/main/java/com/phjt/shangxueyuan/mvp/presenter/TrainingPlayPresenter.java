package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.TrainingCommentBean;
import com.phjt.shangxueyuan.mvp.contract.TrainingPlayContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phsxy.utils.LogUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 01/20/2021 11:57
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class TrainingPlayPresenter extends BasePresenter<TrainingPlayContract.Model, TrainingPlayContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public TrainingPlayPresenter(TrainingPlayContract.Model model, TrainingPlayContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取评论列表
     */
    public void getTrainingComment(String id) {
        mModel.getTrainingComment(id)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<TrainingCommentBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<TrainingCommentBean> baseBean) {
                        if (baseBean.isOk()) {
                            TrainingCommentBean trainingCommentBean = baseBean.data;
                            if (trainingCommentBean != null) {
                                List<TrainingCommentBean.DiaryListBean> diaryList = trainingCommentBean.getDiaryList();
                                mRootView.showTrainingComment(diaryList);
                            } else {
                                mRootView.showTrainingCommentEmpty();
                            }
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    /**
     * 点赞
     */
    public void trainingCommentLike(TrainingCommentBean.DiaryListBean commentBean, int position) {
        mModel.trainingCommentLike(commentBean.getId())
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<String> baseBean) {
                        if (baseBean.isOk()) {
                            //0 未点赞
                            int likestatus = commentBean.getLikestatus();
                            int likeNum = commentBean.getLikeNum();
                            if (likestatus == 0) {
                                commentBean.setLikestatus(1);
                                commentBean.setLikeNum(likeNum + 1);
                            } else {
                                commentBean.setLikestatus(0);
                                commentBean.setLikeNum(Math.max(likeNum - 1, 0));
                            }
                            mRootView.trainingCommentLikeSuccess(commentBean, position);
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    /**
     * 更新观看次数
     */
    public void updateTaskById(String id) {
        mModel.updateTaskById(id)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<String> baseBean) {
                        LogUtils.e("===============更新观看次数：" + baseBean.msg);
                    }
                });
    }

    /**
     * 上报观看时间
     *
     * @param isFinish 是否关闭页面，true则关闭
     */
    public void updateTaskRecordById(String trainingCampId, String secondId, int status, long watchTime, boolean isFinish) {
        mModel.updateTaskRecordById(trainingCampId, secondId, status, watchTime)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<String> baseBean) {
                        if (status == 1) {
                            mRootView.playEndAndRefreshTrainingData();
                        }
                        if (isFinish) {
                            mRootView.updateTimeSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (isFinish) {
                            mRootView.updateTimeSuccess();
                        }
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
