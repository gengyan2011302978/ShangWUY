package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.AdvanceOrderBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.UserAssetsBean;
import com.phjt.shangxueyuan.bean.UserAuthBean;
import com.phjt.shangxueyuan.mvp.contract.OrderConfirmContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 15:51
 * @description :
 */
@ActivityScope
public class OrderConfirmPresenter extends BasePresenter<OrderConfirmContract.Model, OrderConfirmContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public OrderConfirmPresenter(OrderConfirmContract.Model model, OrderConfirmContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 积分汇率
     */
    public void getExchangeRatio() {
        mModel.getExchangeRatio()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<Double>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseBean<Double> baseBean) {
                        if (baseBean.isOk()) {
                            Double ratioBean = baseBean.data;
                            if (ratioBean == null) {
                                ratioBean = 1.0;
                            }
                            mRootView.showExchangeRatio(ratioBean);
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    /**
     * 训练营购买
     *
     * @param commodityId
     * @param buyNum
     * @param payMethod
     * @param commodityMoney
     * @param type
     */
    public void createTrainingOrder(String commodityId, int buyNum, int payMethod, double commodityMoney, int type) {
        mModel.createTrainingOrder(commodityId, buyNum, payMethod, commodityMoney, type)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.paySuccess();
                        } else if (baseBean.code == 85) {
                            mRootView.showCurrencyNotEnough();
                        } else if (baseBean.code == 84) {
                            mRootView.showBoccNotEnough();
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    /**
     * 商品兑换
     *
     * @param virtualCommodityId
     * @param payType
     * @param quantity
     */
    public void exchangeVirtualCommodity(String virtualCommodityId, int payType, int quantity) {
        mModel.exchangeVirtualCommodity(virtualCommodityId, payType, quantity)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.paySuccess();
                        } else if (baseBean.code == 201) {
                            mRootView.showCurrencyNotEnough();
                        } else if (baseBean.code == 202) {
                            mRootView.showBoccNotEnough();
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    /**
     * 提问确认订单
     */
    public void answersConfirmOrder(String teacherId, int payType, int quantity) {
        mModel.answersConfirmOrder(teacherId, payType, quantity)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.paySuccess();
                        } else if (baseBean.code == 201) {
                            mRootView.showCurrencyNotEnough();
                        } else if (baseBean.code == 202) {
                            mRootView.showBoccNotEnough();
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }


    /**
     * 开通直播专栏/ 开通达人 下单
     */
    public void getAddOrder(String commodityId, int payType, int quantity) {
        mModel.getAddOrder(commodityId, payType, quantity)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.paySuccess();
                        } else if (baseBean.code == 201) {
                            mRootView.showCurrencyNotEnough();
                        } else if (baseBean.code == 202) {
                            mRootView.showBoccNotEnough();
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    /**
     * 学豆不足时调用
     * 获取用户资产
     */
    public void getUserAssetsInfo() {
        mModel.getUserAssetsInfo()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<UserAssetsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseBean<UserAssetsBean> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.showUserAssetsAndPay(baseBean.data);
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    /**
     * BOCC不足时调用
     * 是否展示启富通入口
     */
    public void isShowQftPointFlag() {
        mModel.isShowQftPointFlag()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<UserAuthBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<UserAuthBean> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.isShowQftPointFlagSuccess(baseBean.data.getStatus());
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
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
                            mRootView.createStudyCoinOrderSuccess(baseBean.data, payMethod);
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
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
                    public void onNext(BaseBean<AdvanceOrderBean> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.sendRequestYuOrderSuccess(baseBean.data, payType);
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