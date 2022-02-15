package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CourseClassifyBean;
import com.phjt.shangxueyuan.mvp.contract.BocTypeContract;
import com.phjt.shangxueyuan.utils.RxUtils;


/**
 * @author: Created by GengYan
 * date: 10/27/2020 13:53
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class BocTypePresenter extends BasePresenter<BocTypeContract.Model, BocTypeContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public BocTypePresenter(BocTypeContract.Model model, BocTypeContract.View rootView) {
        super(model, rootView);
    }

    public void getCourseClassifyList(String couTypeId) {
        mModel.getCourseClassifyList(couTypeId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<CourseClassifyBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<CourseClassifyBean> bean) {
                        if (bean.isOk()) {
                            mRootView.loadDataSuccess(bean.data);
                        } else {
                            mRootView.showMessage(bean.msg);
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
