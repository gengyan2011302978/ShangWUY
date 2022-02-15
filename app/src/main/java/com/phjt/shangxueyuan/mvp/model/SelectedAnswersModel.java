package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.SelectedAnswersBean;
import com.phjt.shangxueyuan.mvp.contract.SelectedAnswersContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 14:27
 * @description :
 */
@FragmentScope
public class SelectedAnswersModel extends BaseModel implements SelectedAnswersContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public SelectedAnswersModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    /**
     * 查询精选解答列表信息
     */
    @Override
    public Observable<BaseBean<BaseListBean<SelectedAnswersBean>>> getQuestionList(int type, int pageNo, int pageSize, String isRecommend, int timeSort, int likeSort) {
        if (type == 1) {
            return mRepositoryManager.obtainRetrofitService(ApiService.class).getQuestionList(pageNo, pageSize, isRecommend, timeSort, likeSort);
        } else {
            return mRepositoryManager.obtainRetrofitService(ApiService.class).getMyQuestionsList(pageNo, pageSize, isRecommend, timeSort, likeSort);
        }
    }

    /**
     * 点赞
     */
    @Override
    public Observable<BaseBean> getCollectLike(String contentId, int status) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).toLike(contentId, status);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}