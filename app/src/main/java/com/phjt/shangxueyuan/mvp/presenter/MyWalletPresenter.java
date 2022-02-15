package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.WalletMoneyBean;
import com.phjt.shangxueyuan.mvp.contract.MyWalletContract;
import com.phjt.shangxueyuan.utils.RxUtils;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 09:43
 * company: 普华集团
 * description: 我的钱包
 */
@ActivityScope
public class MyWalletPresenter extends BasePresenter<MyWalletContract.Model, MyWalletContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public MyWalletPresenter(MyWalletContract.Model model, MyWalletContract.View rootView) {
        super(model, rootView);
    }

    public void getWalletMoneys() {
        mModel.getWalletMoneys()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<WalletMoneyBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<WalletMoneyBean> baseBean) {
                        if (baseBean.isOk()) {
                            WalletMoneyBean walletMoneyBean = baseBean.data;
                            if (walletMoneyBean != null) {
//                                mRootView.showWithDrawalMoney(walletMoneyBean);
                            }
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    public void applyWithdrawal() {
        mModel.applyWithdrawal()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.applyWithdrawalSuccess();
                        } else if (baseBean.code == 7001) {
                            mRootView.showBankCardDialog();
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    public void getUserAuthSum() {
        mModel.getUserAuthSum()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<String> baseBean) {
                        if (baseBean.isOk()) {
//                            WalletMoneyBean walletMoneyBean = baseBean.data;
//                            if (walletMoneyBean != null) {
                                mRootView.showWithDrawalMoney(baseBean.data);
//                            }
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
