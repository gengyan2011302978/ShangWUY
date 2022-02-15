package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.BindPlanetAccountContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 12/28/2020 13:47
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class BindPlanetAccountModel extends BaseModel implements BindPlanetAccountContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public BindPlanetAccountModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean> bindPlanetAccount(String mobile, String planetNumber) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).bindPlanetNumber(mobile, planetNumber);
    }
}