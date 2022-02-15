package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ExerciseBean;
import com.phjt.shangxueyuan.bean.ExerciseSubmitBean;
import com.phjt.shangxueyuan.mvp.contract.ExerciseDoContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 03/16/2021 09:46
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class ExerciseDoPresenter extends BasePresenter<ExerciseDoContract.Model, ExerciseDoContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public ExerciseDoPresenter(ExerciseDoContract.Model model, ExerciseDoContract.View rootView) {
        super(model, rootView);
    }

    public void getExerciseBookDetail(String id, String couId, String exerciseBookId, String trainingId) {
        mModel.getExerciseBookDetail(id, couId, exerciseBookId, trainingId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<ExerciseBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<ExerciseBean> baseBean) {
                        if (baseBean.isOk()) {
                            ExerciseBean exerciseBean = baseBean.data;
                            if (exerciseBean != null) {
                                mRootView.showExerciseList(exerciseBean, false);
                            } else {
                                mRootView.showMessage("作业数据为空");
                            }
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    public void submitExerciseAnswer(List<ExerciseSubmitBean> submitBeanList) {
        mModel.submitExerciseAnswer(submitBeanList)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<ExerciseBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<ExerciseBean> baseBean) {
                        if (baseBean.isOk()) {
                            ExerciseBean exerciseBean = baseBean.data;
                            if (exerciseBean != null) {
                                mRootView.showExerciseList(exerciseBean, true);
                            }
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
