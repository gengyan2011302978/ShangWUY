package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MessageBean;
import com.phjt.shangxueyuan.bean.ThemeBean;
import com.phjt.shangxueyuan.mvp.contract.CalendarContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 01/28/2021 15:14
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class CalendarPresenter extends BasePresenter<CalendarContract.Model, CalendarContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public CalendarPresenter(CalendarContract.Model model, CalendarContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    public void homeCalendar(String id) {
        mModel.homeCalendar(id)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<ThemeBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<ThemeBean> bean) {
                        if (bean.isOk()) {
                            mRootView.homeCalendarSucceed(bean.data);
                        } else {
                            mRootView.homeCalendarFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.homeCalendarFail();
                    }
                });
    }

}
