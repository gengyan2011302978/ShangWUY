package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.WithdrawalRecordBean;
import com.phjt.shangxueyuan.mvp.contract.WithdrawalDetailContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 17:05
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class WithdrawalDetailModel extends BaseModel implements WithdrawalDetailContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public WithdrawalDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<WithdrawalRecordBean>> getWithdrawalRecordDetail(String id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getWithdrawalRecordDetail(id);
    }
}