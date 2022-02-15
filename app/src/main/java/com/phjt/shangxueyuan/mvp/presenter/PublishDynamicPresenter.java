package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.PublishDynamicContract;
import com.phjt.shangxueyuan.utils.RxUtils;


/**
 * @author: Created by GengYan
 * date: 11/04/2020 16:18
 * company: 普华集团
 * description: 发布动态
 */
@ActivityScope
public class PublishDynamicPresenter extends BasePresenter<PublishDynamicContract.Model, PublishDynamicContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public PublishDynamicPresenter(PublishDynamicContract.Model model, PublishDynamicContract.View rootView) {
        super(model, rootView);
    }

    public void addTheme(String themeName, String topicId, String themeImg) {
        mModel.addTheme(themeName, topicId, themeImg)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.addThemeSuccess();
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
