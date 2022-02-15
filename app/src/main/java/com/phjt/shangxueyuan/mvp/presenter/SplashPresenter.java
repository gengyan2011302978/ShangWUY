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
import com.phjt.shangxueyuan.mvp.contract.SplashContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phsxy.utils.LogUtils;


/**
 * @author: Created by Template
 * date: 03/25/2020 18:00
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class SplashPresenter extends BasePresenter<SplashContract.Model, SplashContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public SplashPresenter(SplashContract.Model model, SplashContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    public void getAnnouncementJson() {
        mModel.getAnnouncementJson()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<MainAnnouncementBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<MainAnnouncementBean> integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.showMaintenanceDialog(integerBaseBean.data);
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
}
