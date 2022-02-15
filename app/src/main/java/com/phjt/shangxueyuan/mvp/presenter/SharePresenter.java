package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MainAnnouncementBean;
import com.phjt.shangxueyuan.mvp.contract.ShareContract;
import com.phjt.shangxueyuan.utils.RxUtils;


/**
 * @author: Created by GengYan
 * date: 07/07/2020 17:32
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class SharePresenter extends BasePresenter<ShareContract.Model, ShareContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public SharePresenter(ShareContract.Model model, ShareContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    public void getConfig() {
        mModel.getConfig()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<String> integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.getConfigSuccess(integerBaseBean.data);
                        } else {
                            mRootView.fail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.fail();
                    }
                });
    }

    public void addUserIntegralRecord() {
        mModel.addUserIntegralRecord()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean integerBaseBean) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.fail();
                    }
                });
    }
}
