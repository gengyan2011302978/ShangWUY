package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ReleaseCoverBean;
import com.phjt.shangxueyuan.bean.TopicMainBean;
import com.phjt.shangxueyuan.mvp.contract.ReleaseTopicContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 10/28/2020 16:28
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class ReleaseTopicPresenter extends BasePresenter<ReleaseTopicContract.Model, ReleaseTopicContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public ReleaseTopicPresenter(ReleaseTopicContract.Model model, ReleaseTopicContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    public void imgConfig() {
        mModel.imgConfig()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<ReleaseCoverBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<ReleaseCoverBean>> baseBean) {
                        if (baseBean.isOk()) {
                            List<ReleaseCoverBean> catalogList = baseBean.data;
                            if (catalogList != null && !catalogList.isEmpty()) {
                                mRootView.imgConfigSuccess(catalogList);
                            }
                        } else {
                        }
                    }
                });
    }

    public void addTopic(int type, String title, String desc, String coverImg) {
        mModel.addTopic(type, title, desc, coverImg)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<String> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.addTopicSuccess(baseBean.data);
                        } else {
                            mRootView.addTopicFaile(baseBean.msg);
                        }
                    }
                });
    }

    public void editTopic(String topicId, String topicName, String focusDescribe, String coverImg) {
        mModel.editTopic(topicId, topicName, focusDescribe, coverImg)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.editTopicSuccess();
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }
}
