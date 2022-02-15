package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MessageDetailBean;
import com.phjt.shangxueyuan.bean.MessageListBean;
import com.phjt.shangxueyuan.mvp.contract.MessageDetalContract;
import com.phjt.shangxueyuan.utils.RxUtils;

/**
 * Created by Template
 *
 * @author :
 * description :消息详情
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/28 09:35
 */

@ActivityScope
public class MessageDetalPresenter extends BasePresenter<MessageDetalContract.Model, MessageDetalContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public MessageDetalPresenter(MessageDetalContract.Model model, MessageDetalContract.View rootView) {
        super(model, rootView);
    }

    public void getMessageDetail( int messageId) {
        mModel.getMessageDetail(messageId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<MessageDetailBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<MessageDetailBean> bean) {
                        if (bean.isOk()) {
                            mRootView.loadSuccess(bean.data);
                        } else {
                            mRootView.loadFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadFail();
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
