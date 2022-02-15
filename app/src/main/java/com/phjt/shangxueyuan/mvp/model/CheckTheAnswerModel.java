package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CheckTheAnswerBean;
import com.phjt.shangxueyuan.mvp.contract.CheckTheAnswerContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 11:05
 * @description :
 */
@ActivityScope
public class CheckTheAnswerModel extends BaseModel implements CheckTheAnswerContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public CheckTheAnswerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    /**
     * 查询精选解答列表信息
     */
    @Override
    public Observable<BaseBean<CheckTheAnswerBean>> getUserQuestion(String answerId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getUserQuestion(answerId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}