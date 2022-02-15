package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyTopicBean;
import com.phjt.shangxueyuan.bean.ParticipatingPunchBean;
import com.phjt.shangxueyuan.mvp.contract.ParticipatingPunchContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:21
 */

@FragmentScope
public class ParticipatingPunchModel extends BaseModel implements ParticipatingPunchContract.Model {

    @Inject
    public ParticipatingPunchModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<BaseListBean<ParticipatingPunchBean>>> getMyPunchCardList(int currentPage, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getMyPunchCardList(currentPage, pageSize);
    }

    @Override
    public Observable<BaseBean<BaseListBean<ParticipatingPunchBean>>> getPunchClockList(String couId, String couType, int currentPage, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getPunchClockList(couId, couType, currentPage, pageSize);
    }

}