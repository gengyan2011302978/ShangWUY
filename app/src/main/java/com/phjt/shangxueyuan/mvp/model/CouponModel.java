package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CouponBean;
import com.phjt.shangxueyuan.mvp.contract.CouponContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 11/24/2020 11:40
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class CouponModel extends BaseModel implements CouponContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public CouponModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<List<CouponBean>>> getCouponList(String commodityTyId, int type, int activityState) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getCouponList(commodityTyId, type, activityState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}