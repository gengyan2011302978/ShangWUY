package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BankCardFillBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.BindingBankCardContract;
import com.phjt.shangxueyuan.utils.RxUtils;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 17:26
 * company: 普华集团
 * description: 绑定银行卡
 */
@ActivityScope
public class BindingBankCardPresenter extends BasePresenter<BindingBankCardContract.Model, BindingBankCardContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public BindingBankCardPresenter(BindingBankCardContract.Model model, BindingBankCardContract.View rootView) {
        super(model, rootView);
    }

    public void getBankCardInfo() {
        mModel.getBankCardInfo()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BankCardFillBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BankCardFillBean> baseBean) {
                        if (baseBean.isOk()) {
                            BankCardFillBean bankCardBean = baseBean.data;
                            if (bankCardBean == null) {
                                mRootView.showBankCardEmpty();
                            } else {
                                mRootView.showBankCardInfo(bankCardBean);
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
