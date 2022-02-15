package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.PointsDetailBean;
import com.phjt.shangxueyuan.bean.TaskListBean;
import com.phjt.shangxueyuan.mvp.contract.GetPointsContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;

import java.util.List;

/**
 * Created by Template
 *
 * @author :
 * description :获得积分
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 15:08
 */

@FragmentScope
public class GetPointsPresenter extends BasePresenter<GetPointsContract.Model, GetPointsContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public GetPointsPresenter(GetPointsContract.Model model, GetPointsContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 查询积分明细
     */
    public void getIntegralRecord(int type,int detailType,int pageNo, int pageSize,boolean isRefresh) {
        mModel.getIntegralRecord( type,detailType,pageNo,  pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<PointsDetailBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<PointsDetailBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.loadIntegralRecordSuccess(bean.data,isRefresh);
                        } else {
                            TipsUtil.show(bean.msg);
                            mRootView.loadIntegralRecordFailure();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadIntegralRecordFailure();
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
