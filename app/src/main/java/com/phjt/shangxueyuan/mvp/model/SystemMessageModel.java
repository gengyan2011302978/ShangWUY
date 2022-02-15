package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MessageListBean;
import com.phjt.shangxueyuan.mvp.contract.SystemMessageContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/20 10:52
 */

@ActivityScope
public class SystemMessageModel extends BaseModel implements SystemMessageContract.Model {


    @Inject
    public SystemMessageModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<MessageListBean>> getListMessage(int type, int pageNo, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getListMessage(type,pageNo,pageSize);
    }
}