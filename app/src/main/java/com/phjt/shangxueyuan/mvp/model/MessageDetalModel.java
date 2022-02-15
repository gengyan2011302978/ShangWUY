package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MessageDetailBean;
import com.phjt.shangxueyuan.bean.MessageListBean;
import com.phjt.shangxueyuan.mvp.contract.MessageDetalContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/28 09:35
 */

@ActivityScope
public class MessageDetalModel extends BaseModel implements MessageDetalContract.Model {


    @Inject
    public MessageDetalModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<MessageDetailBean>> getMessageDetail(int messageId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getMessageDetail(messageId);
    }
}