package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MineExerciseBean;
import com.phjt.shangxueyuan.bean.StudyCampBean;
import com.phjt.shangxueyuan.mvp.contract.StudyCampContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/04/23 09:41
 */

@ActivityScope
public class StudyCampModel extends BaseModel implements StudyCampContract.Model {


    @Inject
    public StudyCampModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<BaseListBean<StudyCampBean>>> getStudyCampList(String trainingCampType,int currentPage, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getStudyCampList(trainingCampType,currentPage, pageSize);
    }
}