package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.LiveBean;
import com.phjt.shangxueyuan.bean.TeacherLiveBean;
import com.phjt.shangxueyuan.mvp.contract.TeacherLiveListContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 04/14/2021 13:50
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class TeacherLiveListModel extends BaseModel implements TeacherLiveListContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public TeacherLiveListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<List<TeacherLiveBean>>> getTeacherLiveList() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getTeacherLiveList();
    }
}