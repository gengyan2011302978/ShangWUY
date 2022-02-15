package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.RegisterContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 03/27/2020 10:20
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class RegisterModel extends BaseModel implements RegisterContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public RegisterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean> sliderValidation(String sessionId, String sig, String token, String scene,String mobile) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).sliderValidation(sessionId, sig, token, scene, mobile,2);

    }

    @Override
    public Observable<BaseBean> userRegister(String mobile, String verificationCode, String password) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).userRegister(mobile, verificationCode, password);
    }

    @Override
    public Observable<BaseBean> getVerificationCode(String mobile, int verificationcodeTypeReg) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getVerificationCode(mobile, verificationcodeTypeReg);
    }
}