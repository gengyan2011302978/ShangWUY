package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ReleaseCoverBean;
import com.phjt.shangxueyuan.mvp.contract.ReleaseTopicContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 10/28/2020 16:28
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class ReleaseTopicModel extends BaseModel implements ReleaseTopicContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public ReleaseTopicModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<List<ReleaseCoverBean>>> imgConfig() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).imgConfig();
    }

    @Override
    public Observable<BaseBean<String>> addTopic(int type, String title, String desc, String coverImg) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).addTopic(type, title, desc, coverImg);
    }

    @Override
    public Observable<BaseBean> editTopic(String topicId, String topicName, String focusDescribe, String coverImg) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .editTopic(topicId, topicName, focusDescribe, coverImg);
    }
}