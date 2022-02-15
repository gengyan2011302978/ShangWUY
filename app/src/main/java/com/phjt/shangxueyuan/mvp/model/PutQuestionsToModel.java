package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ScreenlBean;
import com.phjt.shangxueyuan.mvp.contract.PutQuestionsToContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 14:32
 * @description :
 */
@ActivityScope
public class PutQuestionsToModel extends BaseModel implements PutQuestionsToContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public PutQuestionsToModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }



    /**
     * 筛选
     */
    @Override
    public Observable<BaseBean<List<ScreenlBean>>> getRealmSelectList(String teacherId) {

        return mRepositoryManager.obtainRetrofitService(ApiService.class).getRealmsByTeacherId(teacherId);
    }


    /**
     * 发布问题
     */
    @Override
    public Observable<BaseBean> sendQuestion(String tutorId, String title, String content, String realmId, int isOpen, String questionImg,int payType, int quantity) {

        return mRepositoryManager.obtainRetrofitService(ApiService.class).sendQuestion(tutorId,title,content,realmId,isOpen,questionImg,payType,quantity);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}