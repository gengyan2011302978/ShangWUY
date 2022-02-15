package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.FeedbackContract;
import com.phjt.shangxueyuan.utils.RxUtils;


@ActivityScope
public class FeedbackPresenter extends BasePresenter<FeedbackContract.Model, FeedbackContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public FeedbackPresenter(FeedbackContract.Model model, FeedbackContract.View rootView) {
        super(model, rootView);
    }

    public void submitFeedback(String feedbackContent, String feedbackImg) {
        mModel.submitFeedback(feedbackContent, feedbackImg)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {

                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.submitSuccess();
                        } else {
                            mRootView.submitFailed(baseBean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.submitFailed(e.getMessage());
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
