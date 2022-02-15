package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.AuthenticationContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/05/11 15:02
 */

@ActivityScope
public class AuthenticationModel extends BaseModel implements AuthenticationContract.Model {


    @Inject
    public AuthenticationModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
    @Override
    public Observable<BaseBean> addUserCertificateInfo(String cerFrontUrl,String cerBackUrl,String handCardUrl) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).addUserCertificateInfo(cerFrontUrl, cerBackUrl,handCardUrl);
    }
}