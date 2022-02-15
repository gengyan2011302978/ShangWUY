package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.BindOtherAccountContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 12/28/2020 17:51
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class BindOtherAccountModel extends BaseModel implements BindOtherAccountContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public BindOtherAccountModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean> sliderValidation(String sessionId, String sig, String token, String scene, String mobile) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).sliderValidation(sessionId, sig, token, scene, mobile, 1);

    }

    @Override
    public Observable<BaseBean> bindAndLogin(String mobile, String planetNumber, String verificationCode) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).bindAndLogin(mobile, planetNumber, verificationCode);
    }
}