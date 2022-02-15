package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.AuthenticationContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;

import javax.inject.Inject;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/05/11 15:02
 */

@ActivityScope
public class AuthenticationPresenter extends BasePresenter<AuthenticationContract.Model, AuthenticationContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public AuthenticationPresenter(AuthenticationContract.Model model, AuthenticationContract.View rootView) {
        super(model, rootView);
    }

    public void addUserCertificateInfo(String cerFrontUrl,String cerBackUrl,String handCardUrl) {
        mModel.addUserCertificateInfo(cerFrontUrl, cerBackUrl,handCardUrl)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.addSuccess();
                        } else {
                            if (10017==baseBean.code) {
                                TipsUtil.show("身份证正面（反面）验证失败，请重新上传");
                            }else {
                                TipsUtil.show(baseBean.msg);
                            }
                        }
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
