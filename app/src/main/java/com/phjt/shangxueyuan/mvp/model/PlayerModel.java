package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.PlayerContract;


/**
 * @author: Created by Template
 * date: 03/25/2020 14:40
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class PlayerModel extends BaseModel implements PlayerContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public PlayerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}