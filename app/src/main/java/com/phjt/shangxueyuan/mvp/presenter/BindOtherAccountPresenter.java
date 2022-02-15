package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.BindOtherAccountContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import javax.inject.Inject;


/**
 * @author: Created by Template
 * date: 12/28/2020 17:51
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class BindOtherAccountPresenter extends BasePresenter<BindOtherAccountContract.Model, BindOtherAccountContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public BindOtherAccountPresenter(BindOtherAccountContract.Model model, BindOtherAccountContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 滑块验证
     *
     * @param csessionid csessionid
     * @param sig        sig
     * @param token      token
     * @param scene      scene
     */
    public void sliderValidation(String csessionid, String sig, String token, String scene,String mobile) {
        mModel.sliderValidation(csessionid, sig, token, scene, mobile)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.sliderValidationSuccess();
                        } else {
                            mRootView.sliderValidationFailed(baseBean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                });
    }
    /**
     * 短信登录
     *
     * @param mobile           mobile
     * @param verificationCode verificationCode
     */
    public void bindAndLogin(String mobile, String planetNumber,String verificationCode) {
        mModel.bindAndLogin(mobile,planetNumber, verificationCode)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.bindAndLoginSuccess(String.valueOf(baseBean.data));
                        } else {
                            mRootView.quickLoginFailed(baseBean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        //mRootView.quickLoginFailed(e.getMessage());
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
