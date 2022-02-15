package com.phjt.shangxueyuan.mvp.presenter;


import android.app.Activity;

import com.google.gson.Gson;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.common.CommonHttpManager;
import com.phjt.shangxueyuan.mvp.contract.NotesEditingContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;
import com.phjt.shangxueyuan.utils.FileUploadUtils;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/09/17 18:54
 */

@FragmentScope
public class NotesEditingPresenter extends BasePresenter<NotesEditingContract.Model, NotesEditingContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public NotesEditingPresenter(NotesEditingContract.Model model, NotesEditingContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 添加笔记
     */
    public void addNotess(String courseId, String pointId, String backContent, int openState, String notesImg, long coursePauseTime, int mType, String courseType) {
        mModel.addNotess(courseId, pointId, backContent, openState, notesImg, coursePauseTime, mType, courseType)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.isOk()) {
                            mRootView.addNotessSuccess();
                        } else {
                            TipsUtil.show(bean.msg);
                            mRootView.addNotessFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.addNotessFail();
                    }
                });
    }

    /**
     * 新增课程评论
     */
    public void addComment(String courseId, String content, String imgs, String courseType) {
        mModel.addComment(courseId, content, imgs, courseType)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.addCommentSuccess();
                        } else {
                            TipsUtil.show(baseBean.msg);
                        }
                    }
                });
    }

    /**
     * 训练营新增评论
     */
    public void addTrainingComment(String punchCardId, String otherId, String content, String img) {
        mModel.addTrainingComment(punchCardId, otherId, content, img)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<String> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.addTrainingCommentSuccess();
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
