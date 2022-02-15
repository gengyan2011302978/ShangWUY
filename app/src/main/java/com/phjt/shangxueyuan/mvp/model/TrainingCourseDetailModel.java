package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.TrainingCourseDetailContract;


/**
 * @author: Created by GengYan
 * date: 01/19/2021 10:30
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class TrainingCourseDetailModel extends BaseModel implements TrainingCourseDetailContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public TrainingCourseDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}