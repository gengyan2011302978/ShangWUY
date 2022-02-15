package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.shangxueyuan.bean.AdvanceOrderBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.UserAssetsBean;
import com.phjt.shangxueyuan.bean.UserAuthBean;
import com.phjt.shangxueyuan.mvp.contract.OrderConfirmContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 15:51
 * @description :
 */
@ActivityScope
public class OrderConfirmModel extends BaseModel implements OrderConfirmContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public OrderConfirmModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<Double>> getExchangeRatio() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getExchangeRatio();
    }

    @Override
    public Observable<BaseBean> createTrainingOrder(String commodityId, int buyNum, int payMethod, double commodityMoney, int type) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .createTrainingOrder(commodityId, buyNum, payMethod, commodityMoney, type);
    }

    @Override
    public Observable<BaseBean> exchangeVirtualCommodity(String virtualCommodityId, int payType, int quantity) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .exchangeVirtualCommodity(virtualCommodityId, payType, quantity);
    }

    @Override
    public Observable<BaseBean> answersConfirmOrder(String teacherId, int payType, int quantity) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .answersConfirmOrder(teacherId, payType, quantity);
    }


    @Override
    public Observable<BaseBean<UserAssetsBean>> getUserAssetsInfo() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getUserAssetsInfo();
    }

    @Override
    public Observable<BaseBean<UserAuthBean>> isShowQftPointFlag() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).isShowQftPointFlag();
    }

    @Override
    public Observable<BaseBean<String>> createStudyCoinOrder(String commodityMoney, int payMethod) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).createStudyCoinOrder(commodityMoney,payMethod);
    }

    @Override
    public Observable<BaseBean<AdvanceOrderBean>> sendRequestYuOrder(String orderId, String payType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).sendRequestYuOrder(orderId,payType);
    }

    @Override
    public Observable<BaseBean> getAddOrder(String commodityId, int payType, int quantity) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getAddOrder(commodityId, payType, quantity);
    }
}