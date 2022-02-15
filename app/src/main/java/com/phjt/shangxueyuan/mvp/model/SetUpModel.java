package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.UpdateAppBean;
import com.phjt.shangxueyuan.bean.UserAuthBean;
import com.phjt.shangxueyuan.mvp.contract.SetUpContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


@ActivityScope
public class SetUpModel extends BaseModel implements SetUpContract.Model {


    @Inject
    public SetUpModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<UpdateAppBean>> getCheckVersion() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getCheckVersion(1);
    }

    @Override
    public Observable<BaseBean> outLogin() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).outLogin();
    }

    @Override
    public Observable<BaseBean<UserAuthBean>> isUserAuth(int validType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).isUserAuth(validType);
    }

}