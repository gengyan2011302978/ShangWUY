package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CourseClassifyBean;
import com.phjt.shangxueyuan.mvp.contract.BocTypeContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 10/27/2020 13:53
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class BocTypeModel extends BaseModel implements BocTypeContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public BocTypeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<CourseClassifyBean>> getCourseClassifyList(String couTypeId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getCourseClassifyList(couTypeId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}