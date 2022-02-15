package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ShareImgBean;
import com.phjt.shangxueyuan.bean.TaskCurrencyFirstBean;
import com.phjt.shangxueyuan.bean.UserAssetsBean;
import com.phjt.shangxueyuan.mvp.contract.MyPointsContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 10:54
 */

@ActivityScope
public class MyPointsModel extends BaseModel implements MyPointsContract.Model {

    @Inject
    public MyPointsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<UserAssetsBean>> getUserAssetsInfo() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getUserAssetsInfo();
    }

    @Override
    public Observable<BaseBean<List<TaskCurrencyFirstBean>>> getNewTaskList() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getNewTaskList();
    }

    /**
     * 邀请有礼接口
     */
    @Override
    public Observable<BaseBean<List<ShareImgBean>>> inviteShare() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).inviteShare();

    }

    @Override
    public Observable<BaseBean> inviteShareT() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).inviteShareT();
    }
}