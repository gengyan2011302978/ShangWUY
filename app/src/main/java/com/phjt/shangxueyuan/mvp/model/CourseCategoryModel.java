package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CourseCategoryBean;
import com.phjt.shangxueyuan.bean.OnDemandBean;
import com.phjt.shangxueyuan.bean.SuspendImgBean;
import com.phjt.shangxueyuan.mvp.contract.CourseCategoryContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 05/06/2020 13:55
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class CourseCategoryModel extends BaseModel implements CourseCategoryContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public CourseCategoryModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<CourseCategoryBean>> getCourseCategory(String id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getCourseCategory(id);
    }

    @Override
    public Observable<BaseBean<OnDemandBean>> getOnDemandBean() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getOnDemandBean();
    }


    @Override
    public Observable<BaseBean<SuspendImgBean>> getSuspendImg() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).showSuspendImgInfo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}