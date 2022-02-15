package com.phjt.shangxueyuan.mvp.presenter;


import android.content.Context;
import android.widget.Toast;

import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.UpdateAppBean;
import com.phjt.shangxueyuan.bean.UserAuthBean;
import com.phjt.shangxueyuan.mvp.contract.SetUpContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phsxy.utils.LogUtils;
import com.phsxy.utils.ToastUtils;

import static com.phjt.shangxueyuan.utils.TipsUtil.showTips;


@ActivityScope
public class SetUpPresenter extends BasePresenter<SetUpContract.Model, SetUpContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public SetUpPresenter(SetUpContract.Model model, SetUpContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 检测更新
     */
    public void getCheckVersion(Context context) {
        mModel.getCheckVersion()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<UpdateAppBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<UpdateAppBean> integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.showUpdateDialog(integerBaseBean.data);
                        } else {
                            showTips(integerBaseBean.msg);
                            mRootView.LoadFailed();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        showTips(e.getMessage());
                        mRootView.LoadFailed();
                    }
                });
    }

    public void outLogin(Context context) {
        mModel.outLogin()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {

                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.isOk()) {
                            mRootView.loadOutLoginSuccess();
                            showTips("退出成功");
                        } else {
                            mRootView.loadOutLoginFailed();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadOutLoginFailed();
                    }
                });
    }


    /**
     * 验证用户是否实名认证信息
     */
    public void isUserAuth(int validType) {
        mModel.isUserAuth(validType)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<UserAuthBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<UserAuthBean> integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            if (validType==2) {
                                mRootView.userAuthSuccess(integerBaseBean.data.getStatus());
                            }else if (validType==1){
                                mRootView.passwordSuccess(integerBaseBean.data.getStatus());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mAppManager = null;
    }
}
