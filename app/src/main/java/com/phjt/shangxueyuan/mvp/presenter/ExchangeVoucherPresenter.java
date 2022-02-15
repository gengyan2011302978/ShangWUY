package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.CouponBean;
import com.phjt.shangxueyuan.bean.InitIndexSiteInfoBean;
import com.phjt.shangxueyuan.mvp.contract.ExchangeVoucherContract;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/11/24 14:11
 */

@ActivityScope
public class ExchangeVoucherPresenter extends BasePresenter<ExchangeVoucherContract.Model, ExchangeVoucherContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public ExchangeVoucherPresenter(ExchangeVoucherContract.Model model, ExchangeVoucherContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 精品试听
     */
    public void getInitIndexSiteInfo() {
        mModel.getInitIndexSiteInfo()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<InitIndexSiteInfoBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<InitIndexSiteInfoBean>> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.getInitIndexSiteInfoListSuccess(baseBean.data);
                        } else {
                            if (Constant.LOGOUT_CODE_ERROR != baseBean.code) {
                                mRootView.showMessage(baseBean.msg);
                            }
                        }
                    }
                });

    }

    /**
     * 可用兑换礼券列表
     */
    public void getExchangeCouponList(int type,int currentPage,int pageSize,boolean isRefresh) {
        mModel.getExchangeCouponList(type,currentPage,pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<CouponBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<CouponBean>> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.getExchangeCouponListSuccess(baseBean.data,isRefresh);
                        } else {
                            mRootView.getExchangeCouponListFailed(isRefresh);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.getExchangeCouponListFailed(isRefresh);
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
