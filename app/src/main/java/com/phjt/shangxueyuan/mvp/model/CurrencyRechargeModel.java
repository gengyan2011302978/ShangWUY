package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.AdvanceOrderBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.TaskCurrencyFirstBean;
import com.phjt.shangxueyuan.bean.UserAssetsBean;
import com.phjt.shangxueyuan.mvp.contract.CurrencyRechargeContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/25 09:39
 * @description :
 */
@ActivityScope
public class CurrencyRechargeModel extends BaseModel implements CurrencyRechargeContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public CurrencyRechargeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
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
    public Observable<BaseBean<UserAssetsBean>> getUserAssetsInfo() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getUserAssetsInfo();
    }


}