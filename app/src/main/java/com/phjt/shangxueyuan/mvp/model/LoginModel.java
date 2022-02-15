package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.UpdateAppBean;
import com.phjt.shangxueyuan.bean.UserInfoBean;
import com.phjt.shangxueyuan.mvp.contract.LoginContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 03/26/2020 10:49
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<String>> loginByWeChat(String openId,String unionid) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).loginByWeChat(openId,unionid);
    }

    @Override
    public Observable<BaseBean<Boolean>> checkLoginType() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).checkLoginType();

    }

    @Override
    public Observable<BaseBean> doLoginByPlanetUuid(String uuid) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).doLoginByUuid(uuid);
    }


    @Override
    public Observable<BaseBean> sliderValidation(String sessionId, String sig, String token, String scene, String mobile) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).sliderValidation(sessionId, sig, token, scene, mobile, 1);

    }

    @Override
    public Observable<BaseBean> getVerificationCode(String mobile, int verificationcodeTypeSms) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getVerificationCode(mobile, verificationcodeTypeSms);
    }

    @Override
    public Observable<BaseBean> quickLogin(String mobile, String verificationCode) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).quickLogin(mobile, verificationCode);
    }

    @Override
    public Observable<BaseBean<UserInfoBean>> getUserInfo() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).UserInfoBean();
    }

    @Override
    public Observable<BaseBean<UpdateAppBean>> getCheckVersion() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getCheckVersion(1);
    }
}