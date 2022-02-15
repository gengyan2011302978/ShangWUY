package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.CourseLocalPlayContract;


/**
 * @author: Created by GengYan
 * date: 09/07/2020 09:20
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class CourseLocalPlayModel extends BaseModel implements CourseLocalPlayContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public CourseLocalPlayModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}