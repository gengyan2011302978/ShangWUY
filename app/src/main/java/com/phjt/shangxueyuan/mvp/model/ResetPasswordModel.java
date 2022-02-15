package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.ResetPasswordContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 03/27/2020 13:40
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class ResetPasswordModel extends BaseModel implements ResetPasswordContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public ResetPasswordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean> resetPassword(String phone, String newPassWord) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).resetPassword(phone, newPassWord);
    }

    @Override
    public Observable<BaseBean> changePassword(String phone, String newPassWord) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).changePassword(phone, newPassWord);
    }

    @Override
    public Observable<BaseBean> outLogin() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).outLogin();
    }
}