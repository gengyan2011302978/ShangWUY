package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.MyLearnCurrencyContract;


/**
 * @author: Created by GengYan
 * date: 06/21/2021 16:43
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class MyLearnCurrencyModel extends BaseModel implements MyLearnCurrencyContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public MyLearnCurrencyModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}