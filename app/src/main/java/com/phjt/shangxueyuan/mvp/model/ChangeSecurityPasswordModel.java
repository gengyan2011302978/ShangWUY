package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.ChangeSecurityPasswordContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/05/12 09:54
 */

@ActivityScope
public class ChangeSecurityPasswordModel extends BaseModel implements ChangeSecurityPasswordContract.Model {


    @Inject
    public ChangeSecurityPasswordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean> modifyPassword(String mobile, String originalPwd, String newPassword, String repeatNewPwd, String verificationCode, int type) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).modifyPassword( mobile,  originalPwd,  newPassword,  repeatNewPwd,  verificationCode, type);
    }

}