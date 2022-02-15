package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MessageBean;
import com.phjt.shangxueyuan.bean.MessageListBean;
import com.phjt.shangxueyuan.mvp.contract.MessageContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;

/**
 * @author: Created by Template
 * date: 2020/03/30 13:46
 * company: 普华集团
 * description: 描述
 */

@ActivityScope
public class MessagePresenter extends BasePresenter<MessageContract.Model, MessageContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public MessagePresenter(MessageContract.Model model, MessageContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mErrorHandler = null;
    }

    public void getListMessage() {
        mModel.getListMessage()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<MessageBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<MessageBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.getLoadSucceed(bean.data);
                        } else {
                            mRootView.getLoadFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.getLoadFail();
                    }
                });
    }

}
