package com.phjt.shangxueyuan.mvp.presenter;


import android.annotation.SuppressLint;
import android.app.Activity;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.DiscipleGroupContract;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phsxy.utils.LogUtils;

import static com.phjt.shangxueyuan.utils.TipsUtil.showTips;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/03 17:45
 */

@ActivityScope
public class DiscipleGroupPresenter extends BasePresenter<DiscipleGroupContract.Model, DiscipleGroupContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public DiscipleGroupPresenter(DiscipleGroupContract.Model model, DiscipleGroupContract.View rootView) {
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
    public void sliderValidation(String csessionid, String sig, String token, String scene, String mobile) {
        mModel.sliderValidation(csessionid, sig, token, scene,mobile)
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
                        mRootView.sliderValidationFailed(e.getMessage());
                    }

                });
    }

    /**
     * 发送验证码
     * @param phone
     * @param codeType
     */
    @SuppressLint("CheckResult")
    public void sendCode(String phone, int codeType) {
        mModel.sendCode(phone, codeType).compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(baseBean -> {
                    if (baseBean.isOk()) {
                        mRootView.sendVerficationSuccess();
                    } else {
                        mRootView.sendVerficationFailed(baseBean.msg);
                    }
                }, throwable -> LogUtils.e("send===" + throwable.toString()));

    }



    /**
     *弟子群绑定
     */
    public void getDiscipleGroupAuth(Activity activity, String phone, String code) {
        mModel.getDiscipleGroupAuth( phone,code)
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
