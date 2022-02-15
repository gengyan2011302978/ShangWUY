package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.IntroductionPunchCardsBean;
import com.phjt.shangxueyuan.bean.IntroductionTopCardsBean;
import com.phjt.shangxueyuan.bean.ParticipatingPunchBean;
import com.phjt.shangxueyuan.bean.SaveGeneratePicturesBean;
import com.phjt.shangxueyuan.mvp.contract.IntroductionPunchCardsContract;
import com.phjt.shangxueyuan.utils.RxUtils;

/**
 * Created by Template
 *
 * @author :
 * description :打卡介绍
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/18 10:20
 */

@ActivityScope
public class IntroductionPunchCardsPresenter extends BasePresenter<IntroductionPunchCardsContract.Model, IntroductionPunchCardsContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public IntroductionPunchCardsPresenter(IntroductionPunchCardsContract.Model model, IntroductionPunchCardsContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 打卡介绍
     */
    public void getIntroductionCardst(String punchCardId) {
        mModel.getIntroductionCardst(punchCardId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<IntroductionPunchCardsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<IntroductionPunchCardsBean> integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.LoadSuccess(integerBaseBean.data);
                        } else {
                            mRootView.LoadFailed();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.LoadFailed();
                    }
                });
    }

    /**
     * 获取打卡主页顶部焦点图
     */
    public void getHomeFocus(String punchCardId, String couId) {
        mModel.getHomeFocus(punchCardId, couId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<IntroductionTopCardsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<IntroductionTopCardsBean> integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.LoadPunchCardsSuccess(integerBaseBean.data);
                        } else {
                            mRootView.LoadPunchCardsFailed();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.LoadPunchCardsFailed();
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
