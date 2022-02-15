package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.PointsDetailBean;
import com.phjt.shangxueyuan.bean.TaskListBean;
import com.phjt.shangxueyuan.mvp.contract.GetPointsContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 15:08
 */

@FragmentScope
public class GetPointsModel extends BaseModel implements GetPointsContract.Model {


    @Inject
    public GetPointsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<BaseListBean<PointsDetailBean>>> getIntegralRecord(int type,int detailType,int pageNo, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getIntegralRecord(type,detailType, pageNo, pageSize);
    }
}