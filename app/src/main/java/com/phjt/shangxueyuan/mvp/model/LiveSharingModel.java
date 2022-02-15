package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.LiveShareImgBean;
import com.phjt.shangxueyuan.mvp.contract.LiveSharingContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/04/07 17:27
 */

@ActivityScope
public class LiveSharingModel extends BaseModel implements LiveSharingContract.Model {


    @Inject
    public LiveSharingModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean> addUserIntegralRecord(String id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).addUserIntegralRecord(1, id);
    }
}