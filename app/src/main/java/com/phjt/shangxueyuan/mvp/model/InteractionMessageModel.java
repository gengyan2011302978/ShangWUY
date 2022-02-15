package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MesExerciseBean;
import com.phjt.shangxueyuan.bean.MessageListBean;
import com.phjt.shangxueyuan.bean.ShareImgBean;
import com.phjt.shangxueyuan.mvp.contract.InteractionMessageContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/20 13:54
 */

@ActivityScope
public class InteractionMessageModel extends BaseModel implements InteractionMessageContract.Model {


    @Inject
    public InteractionMessageModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<MessageListBean>> getListMessage(int type, int pageNo, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getListMessage(type,pageNo,pageSize);
    }

    @Override
    public Observable<BaseBean> inviteShareT() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).inviteShareT();
    }

    @Override
    public Observable<BaseBean<MesExerciseBean>> getMesExerciseDetail(String otherId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getMesExerciseDetail(otherId);
    }
    /**
     * 邀请有礼接口
     */
    @Override
    public Observable<BaseBean<List<ShareImgBean>>> inviteShare() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).inviteShare();
    }
}