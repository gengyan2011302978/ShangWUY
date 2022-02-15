package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyTopicBean;
import com.phjt.shangxueyuan.mvp.contract.MyTopicContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 10:10
 */

@ActivityScope
public class MyTopicModel extends BaseModel implements MyTopicContract.Model {


    @Inject
    public MyTopicModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    /**
     * 获取我的话题列表
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Observable<BaseBean<BaseListBean<MyTopicBean>>> getTopicList(int currentPage, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getTopicList(currentPage, pageSize);
    }
}