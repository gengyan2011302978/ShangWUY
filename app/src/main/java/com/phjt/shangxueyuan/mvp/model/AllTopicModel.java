package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.AllTopicContract;


/**
 * @author: Created by GengYan
 * date: 10/28/2020 16:29
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class AllTopicModel extends BaseModel implements AllTopicContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public AllTopicModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}