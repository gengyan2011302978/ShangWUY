package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.PublishDynamicContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 11/04/2020 16:18
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class PublishDynamicModel extends BaseModel implements PublishDynamicContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public PublishDynamicModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean> addTheme(String themeName, String topicId, String themeImg) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .addTheme(themeName, topicId, themeImg);
    }
}