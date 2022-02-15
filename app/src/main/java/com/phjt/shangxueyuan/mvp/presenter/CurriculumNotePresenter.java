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
import com.phjt.shangxueyuan.mvp.contract.CurriculumNoteContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/04 11:19
 */

@FragmentScope
public class CurriculumNotePresenter extends BasePresenter<CurriculumNoteContract.Model, CurriculumNoteContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public CurriculumNotePresenter(CurriculumNoteContract.Model model, CurriculumNoteContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 获取笔记刷新列表
     */
    public void getRefreshList(String courseId, String pointId, int pageNo, int pageSize, int type, boolean isRefresh,String courseType) {
        mModel.getNotesList(courseId, pointId, pageNo, pageSize, type,courseType)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<MyNotesBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<MyNotesBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.loadRefreshDataSuccess(bean.data, pageNo, isRefresh);
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
     * 笔记点赞、取消点赞
     */
    public void thumbsUp(int notesId, int courseId, int position, int likeStatus) {
        mModel.thumbsUp(notesId, courseId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.isOk()) {
                            mRootView.thumbsUpSuccess(position, likeStatus);
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
        this.mAppManager = null;
        this.mErrorHandler = null;
    }
}
