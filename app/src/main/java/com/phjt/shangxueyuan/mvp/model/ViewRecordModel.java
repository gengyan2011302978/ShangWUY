package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ViewRecordBean;
import com.phjt.shangxueyuan.mvp.contract.ViewRecordContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 03/27/2020 17:38
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class ViewRecordModel extends BaseModel implements ViewRecordContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public ViewRecordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<ViewRecordBean>> getViewRecord(int pageNo, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getViewRecord(pageNo,pageSize);
    }
}