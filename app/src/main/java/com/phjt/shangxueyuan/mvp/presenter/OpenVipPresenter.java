package com.phjt.shangxueyuan.mvp.presenter;

import android.annotation.SuppressLint;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.AdvanceOrderBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CouponBean;
import com.phjt.shangxueyuan.bean.PayMethodBean;
import com.phjt.shangxueyuan.mvp.contract.OpenVipContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phsxy.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;


/**
 * @author: Created by Template
 * date: 03/27/2020 15:37
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class OpenVipPresenter extends BasePresenter<OpenVipContract.Model, OpenVipContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public OpenVipPresenter(OpenVipContract.Model model, OpenVipContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 获取支付方式
     */
    public void getPayMethod() {
        mModel.getPayMethod()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<PayMethodBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<PayMethodBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.getPayMethodSuccess(bean.data);
                        } else {
                            mRootView.getPayMethodFailed(bean.msg);
                        }
                    }
                });
    }

    /**
     * 获取优惠卷列表
     */
    public void getCouponList(String commodityTyId, int type, int activityState) {
        mModel.getCouponList(commodityTyId, type, activityState)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<CouponBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<CouponBean>> listBaseBean) {
                        if (listBaseBean.isOk()) {
                            List<CouponBean> couponBeanList = listBaseBean.data;
                            if (couponBeanList != null && !couponBeanList.isEmpty()) {
                                //取第一个优惠卷(默认优惠最大)
                                mRootView.showCoupon(couponBeanList.get(0));
                            } else {
                                mRootView.showCoupon(null);
                            }
                        } else {
                            mRootView.showMessage(listBaseBean.msg);
                            mRootView.showCoupon(null);
                        }
                    }
                });
    }


    /**
     * 购买课程，生成订单详情
     */
    public void requestOrderDetail(String commodityId, int payMethod, int activityState, String userCouponId, int type) {
        mModel.requestOrderDetail(commodityId, payMethod, activityState, userCouponId, type)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<String> order) {
                        if (order.isOk()) {
                            mRootView.requestOrderDetailSuccess(order.data);
                        } else {
                            mRootView.requestOrderDetailFailed(order.msg);
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
                    public void onNext(BaseBean<AdvanceOrderBean> bean) {
                        if (bean.isOk()) {
                            mRootView.sendRequestYuOrderSuccess(bean.data);
                        } else {
                            mRootView.sendRequestYuOrderFailed(bean.msg);
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
