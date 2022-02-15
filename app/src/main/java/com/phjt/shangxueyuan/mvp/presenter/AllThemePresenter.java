package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MotifBean;
import com.phjt.shangxueyuan.bean.ThemeBean;
import com.phjt.shangxueyuan.mvp.contract.AllThemeContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 01/28/2021 16:23
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class AllThemePresenter extends BasePresenter<AllThemeContract.Model, AllThemeContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public AllThemePresenter(AllThemeContract.Model model, AllThemeContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    public void motifList(String id) {
        mModel.motifList(id)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<MotifBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<MotifBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.motifListSucceed(bean.data);
                        } else {
                            mRootView.motifListFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.motifListFail();
                    }
                });
    }

}
