package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.ApplyWithdrawalContract;
import com.phjt.shangxueyuan.utils.RxUtils;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 11:37
 * company: 普华集团
 * description: 申请提现
 */
@ActivityScope
public class ApplyWithdrawalPresenter extends BasePresenter<ApplyWithdrawalContract.Model, ApplyWithdrawalContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public ApplyWithdrawalPresenter(ApplyWithdrawalContract.Model model, ApplyWithdrawalContract.View rootView) {
        super(model, rootView);
    }

    public void confirmWithdrawal(double withdrawalAsset) {
        mModel.confirmWithdrawal(withdrawalAsset)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.confirmWithdrawalSuccess();
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}
