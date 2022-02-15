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
import com.phjt.shangxueyuan.mvp.contract.AllNoteContract;
import com.phjt.shangxueyuan.utils.RxUtils;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/04 11:19
 */

@FragmentScope
public class AllNotePresenter extends BasePresenter<AllNoteContract.Model, AllNoteContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public AllNotePresenter(AllNoteContract.Model model, AllNoteContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取笔记列表
     */
    public void getNotesList(String courseId,int pageNo, int pageSize, boolean isRefresh) {
        mModel.getNotesList(courseId,pageNo, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<MyNotesBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<MyNotesBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.loadDataSuccess(bean.data, isRefresh);
                        } else {
                            mRootView.loadDataFailure();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadDataFailure();
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
