package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.ExchangeCodeBean;
import com.phjt.shangxueyuan.bean.MyNotesBean;
import com.phjt.shangxueyuan.bean.TrainingBattalionExchangeBean;
import com.phjt.shangxueyuan.mvp.contract.CourseExchangeContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 08/06/2020 09:48
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class CourseExchangeModel extends BaseModel implements CourseExchangeContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public CourseExchangeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<ExchangeCodeBean>> getCourseExchangeRecord(int pageNo, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getCourseExchangeRecord(pageNo, pageSize);
    }

    @Override
    public Observable<BaseBean<BaseListBean<TrainingBattalionExchangeBean>>> getRecordList(int pageNo, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getRecordList(pageNo, pageSize);
    }
}