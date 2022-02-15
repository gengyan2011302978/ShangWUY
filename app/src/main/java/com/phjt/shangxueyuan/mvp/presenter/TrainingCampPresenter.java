package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyTopicBean;
import com.phjt.shangxueyuan.bean.MyTrainingCampBean;
import com.phjt.shangxueyuan.mvp.contract.TrainingCampContract;
import com.phjt.shangxueyuan.utils.RxUtils;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:14
 */

@FragmentScope
public class TrainingCampPresenter extends BasePresenter<TrainingCampContract.Model, TrainingCampContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public TrainingCampPresenter(TrainingCampContract.Model model, TrainingCampContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 获取我的专栏/训练营列表
     */
    public void getTrainingBattalionList(int type,int currentPage, int pageSize, boolean isReFresh) {
        mModel.getTrainingBattalionList(type,currentPage, pageSize)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<MyTrainingCampBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<MyTrainingCampBean>> integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.LoadSuccess(integerBaseBean.data, isReFresh);
                        } else {
                            mRootView.LoadFailed(isReFresh);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.LoadFailed(isReFresh);
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
