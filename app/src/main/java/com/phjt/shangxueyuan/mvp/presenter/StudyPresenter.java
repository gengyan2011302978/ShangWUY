package com.phjt.shangxueyuan.mvp.presenter;

import android.annotation.SuppressLint;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CourseTypeBean;
import com.phjt.shangxueyuan.bean.OrdinaryCourseBean;
import com.phjt.shangxueyuan.bean.TrainingBattalionBean;
import com.phjt.shangxueyuan.bean.UserInfoBean;
import com.phjt.shangxueyuan.mvp.contract.StudyContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phsxy.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;


/**
 * @author: Created by Template
 * date: 05/07/2020 14:09
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class StudyPresenter extends BasePresenter<StudyContract.Model, StudyContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public StudyPresenter(StudyContract.Model model, StudyContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取课程分类
     */
    @SuppressLint("CheckResult")
    public void getCourseType() {
        mModel.getCourseType()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<CourseTypeBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<CourseTypeBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.getCourseTypeSuccess(bean.data);
                        } else {
                            mRootView.getCourseTypeFailed(bean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadError();
                    }
                });
    }

    /**
     * 获取课程二级分类
     */
    @SuppressLint("CheckResult")
    public void getChannelCourse(String type) {
        mModel.getChannelCourse(type)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(bean -> {
                    if (bean.isOk()) {
                        mRootView.getChannelCourseSuccess(bean.data);
                    } else {
                        mRootView.getChannelCourseFailed(bean.msg);
                    }
                }, throwable -> LogUtils.e("getChannelCourse===" + throwable.toString()));
    }

    /**
     * 获取普通课程
     */
    @SuppressLint("CheckResult")
    public void getOrdinaryCourse() {
        mModel.getOrdinaryCourse()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<OrdinaryCourseBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<OrdinaryCourseBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.getOrdinaryCourseSuccess(bean.data);
                        } else {
                            mRootView.getOrdinaryCourseFailed(bean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadError();
                    }
                });
    }


    /**
     * 获取训练营记录
     */
    public void getTrainingBattalion() {
        mModel.getTrainingBattalion()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<TrainingBattalionBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<TrainingBattalionBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.getTrainingBattalionSuccess(bean.data);
                        } else {
                            mRootView.getTrainingBattalionFailed();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadError();
                    }
                });
    }

    /**
     * 获取专栏
     */
    public void researchColumn() {
        mModel.researchColumn()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<OrdinaryCourseBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<OrdinaryCourseBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.getResearchColumnSuccess(bean.data);
                        } else {
                            mRootView.getResearchColumnFailed(bean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

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
                            mRootView.loadDataSuccess(bean.data);
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
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}
