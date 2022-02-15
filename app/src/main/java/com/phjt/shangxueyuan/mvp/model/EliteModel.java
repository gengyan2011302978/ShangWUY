package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.EliteContract;


/**
 * @author: Created by GengYan
 * date: 04/02/2020 18:58
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class EliteModel extends BaseModel implements EliteContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public EliteModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}