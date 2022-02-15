package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MainAnnouncementBean;
import com.phjt.shangxueyuan.mvp.contract.SplashContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 03/25/2020 18:00
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class SplashModel extends BaseModel implements SplashContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public SplashModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<MainAnnouncementBean>> getAnnouncementJson() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getAnnouncementJson(BuildConfig.HOST_URL_NOTICE);
    }
}