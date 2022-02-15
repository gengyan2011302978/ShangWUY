package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.ForgetPasswordContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 03/27/2020 13:16
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class ForgetPasswordModel extends BaseModel implements ForgetPasswordContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public ForgetPasswordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean> sendCode(String phone,int codeType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getVerificationCode(phone, codeType);
    }

    @Override
    public Observable<BaseBean> sliderValidation(String sessionId, String sig, String token, String scene,String mobile,int codeType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).sliderValidation(sessionId,sig,token,scene,mobile,codeType);
    }

    @Override
    public Observable<BaseBean<String>> bindingPhone(String mobile, String openId, String code, String imgUrl, String userName,String unionid) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).bindingPhone(mobile,openId,code,imgUrl,userName,unionid);
    }

    @Override
    public Observable<BaseBean> validateCode(String phone, String code, int codeType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).validateCode(phone, code, codeType);
    }
}