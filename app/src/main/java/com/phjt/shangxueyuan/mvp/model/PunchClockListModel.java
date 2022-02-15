package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.PunchClockListContract;


/**
 * @author: Created by GengYan
 * date: 02/05/2021 11:30
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class PunchClockListModel extends BaseModel implements PunchClockListContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public PunchClockListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}