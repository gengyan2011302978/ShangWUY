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
import com.phjt.shangxueyuan.bean.ShareBean;
import com.phjt.shangxueyuan.bean.TopicInfoBean;
import com.phjt.shangxueyuan.mvp.contract.TopicInfoContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 11/03/2020 15:08
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class TopicInfoPresenter extends BasePresenter<TopicInfoContract.Model, TopicInfoContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public TopicInfoPresenter(TopicInfoContract.Model model, TopicInfoContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    public void topicDetail(String topicId) {
        mModel.topicDetail(topicId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<TopicInfoBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<TopicInfoBean> bean) {
                        if (bean.isOk()) {
                            TopicInfoBean topicInfoBean = bean.data;
                            if (topicInfoBean != null) {
                                mRootView.topicDetailSucceed(bean.data);
                            } else {
                                mRootView.showMessage("内容为空");
                            }
                        } else {
                            mRootView.showMessage(bean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.showMessage("网络异常");
                    }
                });
    }

    public void shareTheme(String topicId) {
        mModel.shareTheme(topicId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<ShareBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<ShareBean> bean) {
                        if (bean.isOk()) {
                            mRootView.shareThemeSucceed(bean.data);
                        } else {
                            mRootView.showMessage(bean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.showMessage("网络异常");
                    }
                });
    }
}
