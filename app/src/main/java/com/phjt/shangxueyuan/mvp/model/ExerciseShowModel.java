package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.ExerciseBean;
import com.phjt.shangxueyuan.bean.ExerciseShowBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.mvp.contract.ExerciseShowContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 03/11/2021 11:13
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class ExerciseShowModel extends BaseModel implements ExerciseShowContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public ExerciseShowModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<ExerciseBean>> exerciseBookDetail(String id, String couId, String exerciseBookId, String trainingId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getExerciseBookDetail(id, couId, exerciseBookId, trainingId);
    }

    @Override
    public Observable<BaseBean<ShareItemBean>> getShareexErciseBook(String id, String couId, String trainingId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getShareexErciseBook(id, couId, trainingId);
    }

    @Override
    public Observable<BaseBean<BaseListBean<ExerciseShowBean>>> userAnswer(String type, String exerciseId, String couId, String trainingId, int currentPage, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).
                userAnswer(type, exerciseId, couId, trainingId, currentPage, pageSize);
    }
}