package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ExchangeCodeBean;
import com.phjt.shangxueyuan.bean.VipExchangeCodeBean;
import com.phjt.shangxueyuan.mvp.contract.VipExchangeContract;
import com.phjt.shangxueyuan.utils.RxUtils;


/**
 * @author: Created by GengYan
 * date: 08/06/2020 09:51
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class VipExchangePresenter extends BasePresenter<VipExchangeContract.Model, VipExchangeContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public VipExchangePresenter(VipExchangeContract.Model model, VipExchangeContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    public void getRefreshList(int pageNo, int pageSize, boolean isRefresh) {
        mModel.getVipExchangeRecord(pageNo, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<VipExchangeCodeBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<VipExchangeCodeBean> bean) {
                        if (bean.isOk()) {
                            mRootView.loadRefreshDataSuccess(bean.data, pageNo,isRefresh);
                        } else {
                            mRootView.loadRefreshDataFailure();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadRefreshDataFailure();
                    }
                });
    }
}
