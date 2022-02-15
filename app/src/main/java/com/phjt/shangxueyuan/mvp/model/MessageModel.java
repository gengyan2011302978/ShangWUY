package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MessageBean;
import com.phjt.shangxueyuan.bean.MessageListBean;
import com.phjt.shangxueyuan.mvp.contract.MessageContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author: Created by Template
 * date: 2020/03/30 13:46
 * company: 普华集团
 * description: 描述
 */

@ActivityScope
public class MessageModel extends BaseModel implements MessageContract.Model {


    @Inject
    public MessageModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<List<MessageBean>>> getListMessage() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getMessage();
    }
}