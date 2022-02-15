package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseCourseListBean;
import com.phjt.shangxueyuan.bean.CourseItemBean;
import com.phjt.shangxueyuan.mvp.contract.HotRecommendContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/04/27 17:21
 */

@ActivityScope
public class HotRecommendModel extends BaseModel implements HotRecommendContract.Model {


    @Inject
    public HotRecommendModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<BaseCourseListBean<CourseItemBean>>> getRecommendList(int currentPage, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getRecommendList(currentPage, pageSize);
    }

}