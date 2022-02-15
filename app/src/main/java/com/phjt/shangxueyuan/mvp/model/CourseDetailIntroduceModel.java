package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.CourseCommentBean;
import com.phjt.shangxueyuan.bean.CourseCommentSizeBean;
import com.phjt.shangxueyuan.bean.CourseDetailBean;
import com.phjt.shangxueyuan.mvp.contract.CourseIntroduceContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 15:49
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class CourseDetailIntroduceModel extends BaseModel implements CourseIntroduceContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public CourseDetailIntroduceModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<BaseListBean<CourseCommentBean>>> getCourseCommentList(String courseId, String commentType, int currentPage, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getCourseCommentList(courseId, commentType, currentPage, pageSize);
    }

    @Override
    public Observable<BaseBean<CourseCommentSizeBean>> getCourseCommentSize(String courseId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getCourseCommentSize(courseId);
    }

    @Override
    public Observable<BaseBean> commentZanState(String commentId, int status) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .commentZanState(commentId, status);
    }
}