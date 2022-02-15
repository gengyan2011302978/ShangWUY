package com.phjt.shangxueyuan.mvp.presenter;


import android.app.Activity;

import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.WjjAuthenticationContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import static com.phjt.shangxueyuan.utils.TipsUtil.showTips;


@ActivityScope
public class WjjAuthenticationPresenter extends BasePresenter<WjjAuthenticationContract.Model, WjjAuthenticationContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    public WjjAuthenticationPresenter(WjjAuthenticationContract.Model model, WjjAuthenticationContract.View rootView) {
        super(model, rootView);
    }

    /**
     *挖掘机认证
     */
    public void getWjjAuth(Activity activity,String account, String pwd) {
        mModel.getWjjAuth( account,pwd)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.isOk()) {
                            showTips("认证成功");
                            activity.finish();
                        }else {
                            showTips(bean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        showTips(e.getMessage());
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
