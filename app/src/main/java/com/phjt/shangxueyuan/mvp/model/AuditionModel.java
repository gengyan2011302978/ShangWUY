package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseCourseListBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.CourseItemBean;
import com.phjt.shangxueyuan.bean.CourseTeacherItemBean;
import com.phjt.shangxueyuan.bean.CourseTypeItemBean;
import com.phjt.shangxueyuan.bean.LiveBean;
import com.phjt.shangxueyuan.mvp.contract.AuditionContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 10:49
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class AuditionModel extends BaseModel implements AuditionContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public AuditionModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<BaseCourseListBean<CourseItemBean>>> getCourseList(int level, String couTypeId, Integer sort,
                                                                                  String lecturerId, int currentPage, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getCourseList(level, couTypeId, sort, lecturerId, currentPage, pageSize);
    }

    @Override
    public Observable<BaseBean<List<CourseTypeItemBean>>> getCourseTypeList() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getCourseTypeList();
    }

    @Override
    public Observable<BaseBean<List<CourseTeacherItemBean>>> getCourseTeacherList(String typeId, String lastTypeId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getCourseTeacherList(typeId, lastTypeId);
    }

    @Override
    public Observable<BaseBean<List<LiveBean>>> getStudentLiveList() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getStudentLiveList();
    }

}