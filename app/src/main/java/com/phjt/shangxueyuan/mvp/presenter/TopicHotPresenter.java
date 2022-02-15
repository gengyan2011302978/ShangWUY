package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ExchangeCodeBean;
import com.phjt.shangxueyuan.bean.TopicListBean;
import com.phjt.shangxueyuan.mvp.contract.TopicHotContract;
import com.phjt.shangxueyuan.utils.RxUtils;


/**
 * @author: Created by GengYan
 * date: 10/28/2020 16:42
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class TopicHotPresenter extends BasePresenter<TopicHotContract.Model, TopicHotContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public TopicHotPresenter(TopicHotContract.Model model, TopicHotContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    public void getRefreshList(int pageNo, int pageSize, boolean isRefresh) {
        mModel.getTopicList(pageNo, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<TopicListBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<TopicListBean> bean) {
                        if (bean.isOk()) {
                            mRootView.loadRefreshDataSuccess(bean.data, pageNo,isRefresh);
                        } else {
                            mRootView.loadRefreshDataFailure();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadRefreshDataFailure();
                    }
                });
    }
}
