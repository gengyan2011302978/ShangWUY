package com.phjt.shangxueyuan.mvp.presenter;


import android.content.Context;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyTopicBean;
import com.phjt.shangxueyuan.mvp.contract.MyTopicContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import static com.phjt.shangxueyuan.utils.TipsUtil.showTips;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 10:10
 */

@ActivityScope
public class MyTopicPresenter extends BasePresenter<MyTopicContract.Model, MyTopicContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public MyTopicPresenter(MyTopicContract.Model model, MyTopicContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取我的话题列表
     */
    public void getTopicList(int currentPage, int pageSize, boolean isReFresh) {
        mModel.getTopicList(currentPage, pageSize)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<MyTopicBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<MyTopicBean>> integerBaseBean) {
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
