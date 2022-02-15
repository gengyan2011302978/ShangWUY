package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.WjjAuthenticationContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


@ActivityScope
public class WjjAuthenticationModel extends BaseModel implements WjjAuthenticationContract.Model {


    @Inject
    public WjjAuthenticationModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    /**
     * 挖掘机认证
     */
    @Override
    public Observable<BaseBean> getWjjAuth(String account,String pwd) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getWjjAuth(account,pwd);
    }

}