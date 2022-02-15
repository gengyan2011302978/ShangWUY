package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseCourseListBean;
import com.phjt.shangxueyuan.bean.DataBean;
import com.phjt.shangxueyuan.mvp.contract.DataFragmentContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 06/05/2020 18:12
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class DataFragmentModel extends BaseModel implements DataFragmentContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public DataFragmentModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<BaseCourseListBean<DataBean>>> getDataList(String courseId, int currentPage, int pageSize,String courseType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getDataList(courseId, currentPage, pageSize,courseType);
    }
}