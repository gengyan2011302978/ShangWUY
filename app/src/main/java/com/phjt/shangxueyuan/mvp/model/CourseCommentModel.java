package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.CourseCommentBean;
import com.phjt.shangxueyuan.mvp.contract.CourseCommentContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 05/06/2020 11:32
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class CourseCommentModel extends BaseModel implements CourseCommentContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public CourseCommentModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

//    @Override
//    public Observable<BaseBean<BaseListBean<CourseCommentBean>>> getCourseCommentList(String courseId, int currentPage, int pageSize) {
//        return mRepositoryManager.obtainRetrofitService(ApiService.class)
//                .getCourseCommentList(courseId, currentPage, pageSize);
//    }
//
//    @Override
//    public Observable<BaseBean> addCommentSuccess(String courseId, String content) {
//        return mRepositoryManager.obtainRetrofitService(ApiService.class)
//                .addCommentSuccess(courseId, content);
//    }
//
//    @Override
//    public Observable<BaseBean> commentZanState(String commentId, int status) {
//        return mRepositoryManager.obtainRetrofitService(ApiService.class)
//                .commentZanState(commentId, status);
//    }
}