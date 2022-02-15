package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.WithdrawalRecordBean;
import com.phjt.shangxueyuan.mvp.contract.WithdrawalDetailContract;
import com.phjt.shangxueyuan.utils.RxUtils;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 17:05
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class WithdrawalDetailPresenter extends BasePresenter<WithdrawalDetailContract.Model, WithdrawalDetailContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public WithdrawalDetailPresenter(WithdrawalDetailContract.Model model, WithdrawalDetailContract.View rootView) {
        super(model, rootView);
    }

    public void getWithdrawalDetail(String id) {
        mModel.getWithdrawalRecordDetail(id)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<WithdrawalRecordBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<WithdrawalRecordBean> baseBean) {
                        if (baseBean.isOk()) {
                            WithdrawalRecordBean withdrawalRecordBean = baseBean.data;
                            if (withdrawalRecordBean != null) {
                                mRootView.showWithdrawalDetail(withdrawalRecordBean);
                            }
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
