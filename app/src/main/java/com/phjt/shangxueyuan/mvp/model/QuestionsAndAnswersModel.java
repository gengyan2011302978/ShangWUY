package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.ListBannerBean;
import com.phjt.shangxueyuan.bean.ScreenlBean;
import com.phjt.shangxueyuan.mvp.contract.QuestionsAndAnswersContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 10:56
 * @description :
 */
@ActivityScope
public class QuestionsAndAnswersModel extends BaseModel implements QuestionsAndAnswersContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public QuestionsAndAnswersModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    /**
     * 筛选
     */
    @Override
    public Observable<BaseBean<List<ScreenlBean>>> getRealmSelectList() {

        return mRepositoryManager.obtainRetrofitService(ApiService.class).getRealmSelectList();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}