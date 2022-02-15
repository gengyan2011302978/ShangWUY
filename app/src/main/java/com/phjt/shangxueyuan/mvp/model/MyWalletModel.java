package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.WalletMoneyBean;
import com.phjt.shangxueyuan.mvp.contract.MyWalletContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 09:43
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class MyWalletModel extends BaseModel implements MyWalletContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public MyWalletModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<WalletMoneyBean>> getWalletMoneys() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getWalletMoneys();
    }

    @Override
    public Observable<BaseBean<String>> getUserAuthSum() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getUserAuthSum();
    }

    @Override
    public Observable<BaseBean> applyWithdrawal() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .applyWithdrawal();
    }
}