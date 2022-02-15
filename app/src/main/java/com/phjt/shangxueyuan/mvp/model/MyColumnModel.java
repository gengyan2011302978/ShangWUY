package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ViewColumnBean;
import com.phjt.shangxueyuan.mvp.contract.MyColumnContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 12/09/2020 17:27
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class MyColumnModel extends BaseModel implements MyColumnContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public MyColumnModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<ViewColumnBean>> getMyColumn(int pageNo, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getMyColumn(pageNo,pageSize);
    }
}