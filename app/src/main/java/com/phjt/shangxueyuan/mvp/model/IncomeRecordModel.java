package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.IncomeRecordBean;
import com.phjt.shangxueyuan.mvp.contract.IncomeRecordContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 10:56
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class IncomeRecordModel extends BaseModel implements IncomeRecordContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public IncomeRecordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<BaseListBean<IncomeRecordBean>>> getIncomeRecordList(int currentPage, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getIncomeRecordList(currentPage, pageSize);
    }
}