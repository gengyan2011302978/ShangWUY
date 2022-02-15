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
import com.phjt.shangxueyuan.bean.RankBean;
import com.phjt.shangxueyuan.mvp.contract.RankingContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 01/29/2021 13:57
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class RankingPresenter extends BasePresenter<RankingContract.Model, RankingContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public RankingPresenter(RankingContract.Model model, RankingContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    public void rankingList(String id) {
        mModel.rankingList(id)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<RankBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<RankBean> bean) {
                        if (bean.isOk()) {
                            mRootView.rankingListSucceed(bean.data);
                        } else {
                            mRootView.rankingListFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.rankingListFail();
                    }
                });
    }

}
