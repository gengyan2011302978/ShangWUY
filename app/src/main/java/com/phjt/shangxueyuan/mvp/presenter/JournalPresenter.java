package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.AddDiaryBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MotifDetailBean;
import com.phjt.shangxueyuan.bean.MyDiaryBean;
import com.phjt.shangxueyuan.mvp.contract.JournalContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;

/**
 * Created by Template
 *
 * @author :
 * description :发表日记
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/18 14:49
 */

@ActivityScope
public class JournalPresenter extends BasePresenter<JournalContract.Model, JournalContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public JournalPresenter(JournalContract.Model model, JournalContract.View rootView) {
        super(model, rootView);
    }


    /**
     *获取主题详情详情
     */
    public void getMotifDetails(String diaryId,String punchCardId) {
        mModel.getMotifDetails(diaryId,punchCardId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<MotifDetailBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<MotifDetailBean> bBean) {
                        if (bBean.isOk()) {
                            mRootView.getMotifDetails(bBean.data);
                        } else {
                            mRootView.diaryDetailsFailed();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }

    /**
     *获取日记详情
     */
    public void getDiaryDetails(String diaryId) {
        mModel.getDiaryDetails(diaryId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<MyDiaryBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<MyDiaryBean> bBean) {
                        if (bBean.isOk()) {
                            mRootView.getDiaryDetails(bBean.data);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }
    /**
     * 日记发布/编辑
     */
    public void addDiary(String diaryId, String punchCardId, String strEditDiary, int reissueCardType,String  diaryImg,
                         String calendarDateString,String nodeTaskLinkId,String trainingCampId,String motifId) {
        mModel.addDiary(diaryId, punchCardId, strEditDiary, reissueCardType,diaryImg,calendarDateString,nodeTaskLinkId,trainingCampId,motifId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<AddDiaryBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<AddDiaryBean> integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.addDiarySuccess(integerBaseBean);
                        } else {
                            TipsUtil.show(integerBaseBean.msg);
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
