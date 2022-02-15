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
import com.phjt.shangxueyuan.bean.UserInfoBean;
import com.phjt.shangxueyuan.mvp.contract.BasicInfoContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phsxy.utils.ToastUtils;

import static com.phjt.shangxueyuan.utils.TipsUtil.showTips;


@ActivityScope
public class BasicInfoPresenter extends BasePresenter<BasicInfoContract.Model, BasicInfoContract.View> {

    @Inject
    AppManager mAppManager;

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public BasicInfoPresenter(BasicInfoContract.Model model, BasicInfoContract.View rootView) {
        super(model, rootView);
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
                            mRootView.loadDataSuccess(bean.data);
                        } else {
                            mRootView.showMessage(bean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     * 编辑用户基本信息
     */
    public void onClickUserEdit(Context context, String nickName, String userName, int sex, String photo) {
        mModel.onClickUserEdit(nickName, userName, sex, photo)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.isOk()) {
                            showTips(bean.msg);
                            mRootView.userEditSuccess();
                        } else {
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
