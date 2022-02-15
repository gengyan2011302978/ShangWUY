package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.IntegralRankingBean;
import com.phjt.shangxueyuan.bean.PointsDetailBean;
import com.phjt.shangxueyuan.mvp.contract.RankingListsContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;

import java.util.List;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 15:19
 */

@ActivityScope
public class RankingListsPresenter extends BasePresenter<RankingListsContract.Model, RankingListsContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public RankingListsPresenter(RankingListsContract.Model model, RankingListsContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 查询积分明细
     */
    public void getIntegralRanking() {
        mModel.getIntegralRanking()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<IntegralRankingBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<IntegralRankingBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.loadSuccess(bean.data);
                        } else {
                            TipsUtil.show(bean.msg);
                            mRootView.loadFailure();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadFailure();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mErrorHandler = null;
    }
}
