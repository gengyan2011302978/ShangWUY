package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MineExerciseBean;
import com.phjt.shangxueyuan.mvp.contract.ExerciseListContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 03/22/2021 16:26
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class ExerciseListModel extends BaseModel implements ExerciseListContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public ExerciseListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<BaseListBean<MineExerciseBean>>> exerciseBookList(String couId, String couType, int currentPage, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).exerciseBookList(couId,couType, currentPage, pageSize);
    }
}