package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.DiscipleGroupContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/03 17:45
 */

@ActivityScope
public class DiscipleGroupModel extends BaseModel implements DiscipleGroupContract.Model {


    @Inject
    public DiscipleGroupModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean> sendCode(String phone, int codeType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getVerificationCode(phone, codeType);
    }

    @Override
    public Observable<BaseBean> sliderValidation(String sessionId, String sig, String token, String scene,String mobile) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).sliderValidation(sessionId,sig,token,scene, mobile,7);
    }

    /**
     *弟子群绑定
     */
    @Override
    public Observable<BaseBean> getDiscipleGroupAuth(String phone, String code) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getDiscipleGroupAuth(phone, code);
    }

}