package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ThemeBean;
import com.phjt.shangxueyuan.mvp.contract.CalendarContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 01/28/2021 15:14
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class CalendarModel extends BaseModel implements CalendarContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public CalendarModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<ThemeBean>> homeCalendar(String id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).homeCalendar(id);
    }
}