package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.BindPlanetAccountContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import javax.inject.Inject;


/**
 * @author: Created by Template
 * date: 12/28/2020 13:47
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class BindPlanetAccountPresenter extends BasePresenter<BindPlanetAccountContract.Model, BindPlanetAccountContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public BindPlanetAccountPresenter(BindPlanetAccountContract.Model model, BindPlanetAccountContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 短信登录
     *
     * @param mobile           mobile
     * @param planetNumber planetNumber
     */
    public void bindPlanetAccount(String mobile, String planetNumber) {
        mModel.bindPlanetAccount(mobile, planetNumber)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.bindPlanetSuccess(String.valueOf(baseBean.data));
                        } else {
                            mRootView.bindPlanetFail(baseBean.msg);
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
