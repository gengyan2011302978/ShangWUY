package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.UserAssetsBean;
import com.phjt.shangxueyuan.mvp.contract.MallHomeContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 14:43
 * @description :
 */
@ActivityScope
public class MallHomeModel extends BaseModel implements MallHomeContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public MallHomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<UserAssetsBean>> getUserAssetsInfo() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getUserAssetsInfo();
    }
}