package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.UpdateAppBean;
import com.phjt.shangxueyuan.bean.UserInfoBean;
import com.phjt.shangxueyuan.mvp.contract.LoginContract;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.RxUtils;

import javax.inject.Inject;


/**
 * @author: Created by Template
 * date: 03/26/2020 10:49
 * company: 普华集团
 * description: login p
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    public void weChatLogin(String mOpenId,String unionid) {
        mModel.loginByWeChat(mOpenId,unionid).compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<String> tokenBeanBaseBean) {
                        if (tokenBeanBaseBean != null) {
                            if (tokenBeanBaseBean.isOk() && tokenBeanBaseBean.data != null) {
                                mRootView.loginSuccess(tokenBeanBaseBean.data);
                            } else {
                                mRootView.loginFailedByWx(tokenBeanBaseBean.msg);
                            }
                        }
                    }
                });
    }

    public void checkLoginType() {
        mModel.checkLoginType()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<Boolean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<Boolean> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.checkLoginTypeSuccess(baseBean.data);
                        } else {
                            mRootView.doLoginFailed(baseBean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        //mRootView.doLoginFailed(e.getMessage());
                    }

                });
    }

    public void doLoginByPlanetUuid(String uuid) {
        mModel.doLoginByPlanetUuid(uuid)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.doLoginSuccess(String.valueOf(baseBean.data));
                        } else {
                            mRootView.doLoginFailed(baseBean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                });
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
                        //mRootView.sliderValidationFailed(e.getMessage());
                    }

                });
    }

    /**
     * 获取验证码
     *
     * @param mobile mobile
     */
    public void getVerificationCode(String mobile) {
        mModel.getVerificationCode(mobile, Constant.VERIFICATIONCODE_TYPE_SMS)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.getVerificationCodeSuccess();
                        } else {
                            mRootView.getVerificationCodeFailed(baseBean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        //mRootView.getVerificationCodeFailed(e.getMessage());
                    }

                });
    }

    /**
     * 获取用户基本信息
     */
    public void getUserInfo() {
        mModel.getUserInfo()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<UserInfoBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<UserInfoBean> bean) {
                        if (bean.isOk()) {
                            mRootView.getUserInfoSuccess(bean.data);
                        }
                    }
                });
    }

    /**
     * 短信登录
     *
     * @param mobile           mobile
     * @param verificationCode verificationCode
     */
    public void quickLogin(String mobile, String verificationCode) {
        mModel.quickLogin(mobile, verificationCode)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.quickLoginSuccess(String.valueOf(baseBean.data));
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

    /**
     * 升级接口
     */
    public void getCheckVersion() {
        mModel.getCheckVersion()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<UpdateAppBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<UpdateAppBean> integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.showUpdateDialog(integerBaseBean.data);
                        }
                    }
                });
    }
}
