package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ShareBean;
import com.phjt.shangxueyuan.bean.TopicInfoBean;
import com.phjt.shangxueyuan.mvp.contract.TopicInfoContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 11/03/2020 15:08
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class TopicInfoModel extends BaseModel implements TopicInfoContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public TopicInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<TopicInfoBean>> topicDetail(String topicId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).topicDetail(topicId);
    }

    @Override
    public Observable<BaseBean<ShareBean>> shareTheme(String topicId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).shareTheme(topicId);
    }
}