package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MallCommodityBean;
import com.phjt.shangxueyuan.mvp.contract.MallListContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 16:22
 * @description :
 */
@FragmentScope
public class MallListModel extends BaseModel implements MallListContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public MallListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<BaseListBean<MallCommodityBean>>> getMallCommodityList(int commodityType, int currentPage, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getMallCommodityList(commodityType, currentPage, pageSize);
    }

    @Override
    public Observable<BaseBean<BaseListBean<MallCommodityBean>>> getExchangeRecordList(int currentPage, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getExchangeRecordList(currentPage, pageSize);
    }
}