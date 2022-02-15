package com.phjt.shangxueyuan.mvp.presenter;


import android.app.Activity;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyNotesBean;
import com.phjt.shangxueyuan.mvp.contract.ScannerContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/17 09:45
 */

@ActivityScope
public class ScannerPresenter extends BasePresenter<ScannerContract.Model, ScannerContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public ScannerPresenter(ScannerContract.Model model, ScannerContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 扫一扫
     */
    public void getScanQRcode(String certificate) {
        mModel.getScanQRcode(certificate)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.isOk()) {
                            mRootView.loadDataSuccess();
                        } else {
                            TipsUtil.show(bean.msg);
                            mRootView.loadDataFailure();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadDataFailure();
                    }
                });
    }

    /**
     * 扫一扫-确认/取消
     */
    public void getScanRcodeConfirm(String certificate, int optionType, Activity activity, boolean isCancel) {
        mModel.getScanRcodeConfirm(certificate, optionType)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.isOk()) {
                            activity.finish();
                            if (!isCancel) {
                                TipsUtil.show(bean.msg);
                            }
                        } else {
                            TipsUtil.show(bean.msg);
                            if (isCancel) {
                                activity.finish();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (isCancel) {
                            activity.finish();
                        }
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
