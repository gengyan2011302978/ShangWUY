package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.TrainingCommentBean;
import com.phjt.shangxueyuan.mvp.contract.TrainingPlayContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 01/20/2021 11:57
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class TrainingPlayModel extends BaseModel implements TrainingPlayContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public TrainingPlayModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<TrainingCommentBean>> getTrainingComment(String id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getTrainingComment(id);
    }

    @Override
    public Observable<BaseBean<String>> trainingCommentLike(String id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .trainingCommentLike(id);
    }

    @Override
    public Observable<BaseBean<String>> updateTaskById(String id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .updateTaskById(id);
    }

    @Override
    public Observable<BaseBean<String>> updateTaskRecordById(String trainingCampId, String secondId, int status, long watchTime) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .updateTaskRecordById(trainingCampId, secondId, status, watchTime);
    }
}