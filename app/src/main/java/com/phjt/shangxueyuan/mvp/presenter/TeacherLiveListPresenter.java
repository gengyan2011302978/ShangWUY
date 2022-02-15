package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.LiveBean;
import com.phjt.shangxueyuan.bean.TeacherLiveBean;
import com.phjt.shangxueyuan.mvp.contract.TeacherLiveListContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 04/14/2021 13:50
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class TeacherLiveListPresenter extends BasePresenter<TeacherLiveListContract.Model, TeacherLiveListContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public TeacherLiveListPresenter(TeacherLiveListContract.Model model, TeacherLiveListContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    public void getTeacherLiveList() {
        mModel.getTeacherLiveList()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<TeacherLiveBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<TeacherLiveBean>> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.getTeacherLiveListSuccess(baseBean.data);
                        } else {

                        }
                    }
                });
    }
}
