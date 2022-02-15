package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ExchangeCodeBean;
import com.phjt.shangxueyuan.bean.TopicListBean;
import com.phjt.shangxueyuan.mvp.contract.TopicTimeContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 10/28/2020 16:43
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class TopicTimeModel extends BaseModel implements TopicTimeContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public TopicTimeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<TopicListBean>> getTopicList(int pageNo, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getTopicList(2,pageNo,pageSize);
    }
}