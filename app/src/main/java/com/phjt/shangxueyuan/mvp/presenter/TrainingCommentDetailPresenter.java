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
import com.phjt.shangxueyuan.mvp.contract.TrainingCommentDetailContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 02/04/2021 11:17
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class TrainingCommentDetailPresenter extends BasePresenter<TrainingCommentDetailContract.Model, TrainingCommentDetailContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public TrainingCommentDetailPresenter(TrainingCommentDetailContract.Model model, TrainingCommentDetailContract.View rootView) {
        super(model, rootView);
    }

    public void getCommentReplyList(String id) {
        mModel.getCommentReplyList(id)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<TrainingCommentBean.ReplyVoListBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<TrainingCommentBean.ReplyVoListBean>> baseBean) {
                        if (baseBean.isOk()) {
                            List<TrainingCommentBean.ReplyVoListBean> replyVoList = baseBean.data;
                            mRootView.getReplyList(replyVoList);
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    public void addCommentReply(String id, String replyContent) {
        mModel.addCommentReply(id, replyContent)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<String> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.addReplySuccess();
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    /**
     * 回复点赞
     */
    public void replyLike(TrainingCommentBean.ReplyVoListBean replyVoListBean, int position) {
        mModel.replyLike(replyVoListBean.getId())
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<String> baseBean) {
                        if (baseBean.isOk()) {
                            int likestatus = replyVoListBean.getLikestatus();
                            int likeNum = replyVoListBean.getLikeNum();
                            if (likestatus == 0) {
                                replyVoListBean.setLikestatus(1);
                                replyVoListBean.setLikeNum(likeNum + 1);
                            } else {
                                replyVoListBean.setLikestatus(0);
                                replyVoListBean.setLikeNum(Math.max(likeNum - 1, 0));
                            }
                            mRootView.replyLikeChange(replyVoListBean, position);
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    /**
     * 评论点赞
     */
    public void trainingCommentLike(String id) {
        mModel.trainingCommentLike(id)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<String> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.trainingCommentLikeSuccess();
                        } else {
                            mRootView.showMessage(baseBean.msg);
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
