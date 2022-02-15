package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseCourseListBean;
import com.phjt.shangxueyuan.bean.CourseCatalogFirstBean;
import com.phjt.shangxueyuan.mvp.contract.CourseCatalogContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 15:52
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class CourseCatalogModel extends BaseModel implements CourseCatalogContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public CourseCatalogModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<List<CourseCatalogFirstBean>>> getCourseCalalogList(String courseId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getCourseCalalogList(courseId);
    }
}