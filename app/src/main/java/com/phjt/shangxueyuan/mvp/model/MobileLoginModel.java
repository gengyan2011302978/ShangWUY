package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.MobileLoginContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 03/30/2020 10:42
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class MobileLoginModel extends BaseModel implements MobileLoginContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public MobileLoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }


    @Override
    public Observable<BaseBean> doLogin(String mobile, String password) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).doLogin(mobile,password);
    }

    @Override
    public Observable<BaseBean<String>> loginByWeChat(String openId,String unionid) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).loginByWeChat(openId,unionid);
    }
}