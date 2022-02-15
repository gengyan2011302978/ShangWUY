package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyNotesBean;
import com.phjt.shangxueyuan.bean.NotesDetailsBean;
import com.phjt.shangxueyuan.mvp.contract.NotesReviewDetailContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/18 13:44
 */

@FragmentScope
public class NotesReviewDetailPresenter extends BasePresenter<NotesReviewDetailContract.Model, NotesReviewDetailContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public NotesReviewDetailPresenter(NotesReviewDetailContract.Model model, NotesReviewDetailContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取笔记详情
     */
    public void getnotesDetails(int notesId, int pageNo, int pageSize, boolean refurbish) {
        mModel.getnotesDetails(notesId, pageNo, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<NotesDetailsBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<NotesDetailsBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.loadDetailsSuccess(bean.data, refurbish);
                        } else {
                            mRootView.loadDetailsFailure(refurbish);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadDetailsFailure(refurbish);
                    }
                });
    }


    /**
     * 回复笔记
     */
    public void replyNotess(int notesId, String backContent,int courseId) {
        mModel.replyNotess(notesId, backContent,courseId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean  bean) {
                        if (bean.isOk()) {
                            mRootView.replyNotesSuccess();
                            TipsUtil.show("回复成功");
                        } else {
                            TipsUtil.show(bean.msg);
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
