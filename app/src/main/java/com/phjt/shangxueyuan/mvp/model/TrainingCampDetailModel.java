package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseCourseListBean;
import com.phjt.shangxueyuan.bean.DataBean;
import com.phjt.shangxueyuan.bean.ShareBean;
import com.phjt.shangxueyuan.bean.TrainingDetailBean;
import com.phjt.shangxueyuan.mvp.contract.TrainingCampDetailContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 01/18/2021 13:53
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class TrainingCampDetailModel extends BaseModel implements TrainingCampDetailContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public TrainingCampDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<TrainingDetailBean>> getTrainingDetail(String id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getTrainingDetail(id);
    }

    @Override
    public Observable<BaseBean<ShareBean>> getTrainingShare(String id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getTrainingShare(id);
    }

    @Override
    public Observable<BaseBean<String>> trainingFreeBuy(String trainingCampId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .trainingFreeBuy(trainingCampId);
    }

    @Override
    public Observable<BaseBean<BaseCourseListBean<DataBean>>> getDataList(String courseId, int currentPage, int pageSize, String courseType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getDataList(courseId, currentPage, pageSize,courseType);
    }
}