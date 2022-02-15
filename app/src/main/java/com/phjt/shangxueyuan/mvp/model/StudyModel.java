package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CourseChannelBean;
import com.phjt.shangxueyuan.bean.CourseTypeBean;
import com.phjt.shangxueyuan.bean.OrdinaryCourseBean;
import com.phjt.shangxueyuan.bean.TrainingBattalionBean;
import com.phjt.shangxueyuan.bean.UserInfoBean;
import com.phjt.shangxueyuan.mvp.contract.StudyContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 05/07/2020 14:09
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class StudyModel extends BaseModel implements StudyContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public StudyModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<List<CourseTypeBean>>> getCourseType() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getCourseType();
    }

    @Override
    public Observable<BaseBean<List<CourseChannelBean>>> getChannelCourse(String couTypeId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getChannelCourse(couTypeId);
    }
    @Override
    public Observable<BaseBean<List<OrdinaryCourseBean>>> getOrdinaryCourse() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getOrdinaryCourse();
    }

    @Override
    public Observable<BaseBean<List<OrdinaryCourseBean>>> researchColumn() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getResearchColumn();
    }

    @Override
    public Observable<BaseBean<UserInfoBean>> getUserInfo() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).UserInfoBean();
    }

    /**
     * 获取训练营记录
     */
    @Override
    public Observable<BaseBean<List<TrainingBattalionBean>>> getTrainingBattalion() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getTrainingBattalion();
    }


}