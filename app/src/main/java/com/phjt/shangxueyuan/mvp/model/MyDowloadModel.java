package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.MyDowloadContract;


/**
 * @author: Created by Template
 * date: 06/02/2020 10:34
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class MyDowloadModel extends BaseModel implements MyDowloadContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public MyDowloadModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}