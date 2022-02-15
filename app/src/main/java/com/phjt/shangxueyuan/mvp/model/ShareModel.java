package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MainAnnouncementBean;
import com.phjt.shangxueyuan.mvp.contract.ShareContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 07/07/2020 17:32
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class ShareModel extends BaseModel implements ShareContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public ShareModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<String>> getConfig() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getConfig("WX_INVITE_BACKGROUND_IMAGE");
    }

    @Override
    public Observable<BaseBean> addUserIntegralRecord() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).addUserIntegralRecord(2, "");
    }
}