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
import com.phjt.shangxueyuan.mvp.contract.CourseClassifyContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import static com.phjt.shangxueyuan.utils.TipsUtil.showTips;


/**
 * @author: Created by GengYan
 * date: 05/06/2020 17:36
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class CourseClassifyPresenter extends BasePresenter<CourseClassifyContract.Model, CourseClassifyContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public CourseClassifyPresenter(CourseClassifyContract.Model model, CourseClassifyContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 证书频道详情
     */
    public void getCourseClassifyList(String couTypeId) {
        mModel.getCourseClassifyList(couTypeId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<CourseClassifyBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<CourseClassifyBean> bean) {
                        if (bean.isOk()) {
                            mRootView.loadDataSuccess(bean.data);
                        } else {
                            mRootView.loadDataFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadDataFail();
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
