package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.AdvanceOrderBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.UserAssetsBean;
import com.phjt.shangxueyuan.mvp.contract.CurrencyRechargeContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import org.jetbrains.annotations.NotNull;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/25 09:39
 * @description :
 */
@ActivityScope
public class CurrencyRechargePresenter extends BasePresenter<CurrencyRechargeContract.Model, CurrencyRechargeContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public CurrencyRechargePresenter(CurrencyRechargeContract.Model model, CurrencyRechargeContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 充值学豆
     */
    public void createStudyCoinOrder(String commodityMoney, int payMethod) {
        mModel.createStudyCoinOrder(commodityMoney, payMethod)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<String> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.createStudyCoinOrderSuccess(baseBean.data,payMethod);
                        } else {
                            mRootView.createStudyCoinOrderFailed(baseBean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.createStudyCoinOrderFailed(e.getMessage());
                    }

                });
    }

    /**
     * 生成预付订单
     */
    public void sendRequestYuOrder(String orderId, String payType) {
        mModel.sendRequestYuOrder(orderId, payType)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<AdvanceOrderBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<AdvanceOrderBean> bean) {
                        if (bean.isOk()) {
                            mRootView.sendRequestYuOrderSuccess(bean.data,payType);
                        } else {
                            mRootView.sendRequestYuOrderFailed(bean.msg);
                        }
                    }
                });
    }

    /**
     * 用户资产
     */
    public void getUserAssetsInfo() {
        mModel.getUserAssetsInfo()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<UserAssetsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseBean<UserAssetsBean> baseBean) {
                        if (baseBean.isOk()) {
                            if (baseBean.data != null) {
                                mRootView.showUserAssets(baseBean.data);
                            } else {
                                mRootView.showMessage("资产数据为空");
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