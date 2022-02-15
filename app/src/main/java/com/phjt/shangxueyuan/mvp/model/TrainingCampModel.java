package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyTopicBean;
import com.phjt.shangxueyuan.bean.MyTrainingCampBean;
import com.phjt.shangxueyuan.mvp.contract.TrainingCampContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:14
 */

@FragmentScope
public class TrainingCampModel extends BaseModel implements TrainingCampContract.Model {


    @Inject
    public TrainingCampModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
    /**
     * 获取个人中心—我的专栏/训练营列表
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Observable<BaseBean<BaseListBean<MyTrainingCampBean>>> getTrainingBattalionList(int type, int currentPage, int pageSize){
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getTrainingBattalionList(type,currentPage, pageSize);
    }
}