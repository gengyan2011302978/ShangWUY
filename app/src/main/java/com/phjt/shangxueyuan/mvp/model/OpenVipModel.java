package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.shangxueyuan.bean.AdvanceOrderBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CouponBean;
import com.phjt.shangxueyuan.bean.PayMethodBean;
import com.phjt.shangxueyuan.mvp.contract.OpenVipContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 03/27/2020 15:37
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class OpenVipModel extends BaseModel implements OpenVipContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public OpenVipModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<String>> requestOrderDetail(String commodityId, int payMethod, int activityState, String userCouponId, int type) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).
                requestOrderDetail(commodityId, payMethod, activityState, userCouponId, type);
    }

    @Override
    public Observable<BaseBean<AdvanceOrderBean>> sendRequestYuOrder(String orderId, String payType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).sendRequestYuOrder(orderId, payType);
    }

    @Override
    public Observable<BaseBean<List<PayMethodBean>>> getPayMethod() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getPayMethod(1);
    }

    @Override
    public Observable<BaseBean<List<CouponBean>>> getCouponList(String commodityTyId, int type, int activityState) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getCouponList(commodityTyId, type, activityState);
    }
}