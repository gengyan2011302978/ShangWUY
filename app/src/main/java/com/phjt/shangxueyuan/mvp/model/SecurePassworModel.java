package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.SecurePassworContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/05/12 11:18
 */

@ActivityScope
public class SecurePassworModel extends BaseModel implements SecurePassworContract.Model {


    @Inject
    public SecurePassworModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
    @Override
    public Observable<BaseBean> sliderValidation(String sessionId, String sig, String token, String scene, String mobile, int codeType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).sliderValidation(sessionId,sig,token,scene,mobile,codeType);
    }

    @Override
    public Observable<BaseBean> modifyPassword(String mobile, String originalPwd, String newPassword, String repeatNewPwd, String verificationCode,int type) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).modifyPassword( mobile,  originalPwd,  newPassword,  repeatNewPwd,  verificationCode, type);
    }
}