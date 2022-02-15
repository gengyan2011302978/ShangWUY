package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CouponBean;
import com.phjt.shangxueyuan.mvp.contract.CouponContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 11/24/2020 11:40
 * company: 普华集团
 * description: 优惠券页面
 */
@ActivityScope
public class CouponPresenter extends BasePresenter<CouponContract.Model, CouponContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public CouponPresenter(CouponContract.Model model, CouponContract.View rootView) {
        super(model, rootView);
    }

    public void getCouponList(String commodityTyId, int type, int activityState) {
        mModel.getCouponList(commodityTyId, type, activityState)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<CouponBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<CouponBean>> listBaseBean) {
                        if (listBaseBean.isOk()) {
                            List<CouponBean> couponBeanList = listBaseBean.data;
                            if (couponBeanList != null && !couponBeanList.isEmpty()) {
                                mRootView.showCouponList(couponBeanList);
                            } else {
                                mRootView.showEmptyView();
                            }
                        } else {
                            mRootView.showEmptyView();
                            mRootView.showMessage(listBaseBean.msg);
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
