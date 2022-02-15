package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ExerciseBean;
import com.phjt.shangxueyuan.bean.ExerciseSubmitBean;
import com.phjt.shangxueyuan.mvp.contract.ExerciseDoContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * @author: Created by GengYan
 * date: 03/16/2021 09:46
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class ExerciseDoModel extends BaseModel implements ExerciseDoContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public ExerciseDoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<ExerciseBean>> getExerciseBookDetail(String id, String couId, String exerciseBookId, String trainingId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getExerciseBookDetail(id, couId, exerciseBookId, trainingId);
    }

    @Override
    public Observable<BaseBean<ExerciseBean>> submitExerciseAnswer(List<ExerciseSubmitBean> submitBeanList) {
        MediaType contentType = MediaType.parse("application/json;charset=UTF-8");
        RequestBody requestBody = RequestBody.create(contentType, new Gson().toJson(submitBeanList));

        return mRepositoryManager.obtainRetrofitService(ApiService.class).submitExerciseAnswer(requestBody);
    }
}