package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.CouponBean;
import com.phjt.shangxueyuan.bean.InitIndexSiteInfoBean;
import com.phjt.shangxueyuan.mvp.contract.ExchangeVoucherContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/11/24 14:11
 */

@ActivityScope
public class ExchangeVoucherModel extends BaseModel implements ExchangeVoucherContract.Model {


    @Inject
    public ExchangeVoucherModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    /**
     * 精品试听
     */
    @Override
    public Observable<BaseBean<List<InitIndexSiteInfoBean>>> getInitIndexSiteInfo() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getInitIndexSiteInfo();
    }

    /**
     * 可用兑换礼券列表
     */
    @Override
    public Observable<BaseBean<BaseListBean<CouponBean>>> getExchangeCouponList(int type,int currentPage,int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getExchangeCouponList(type, currentPage,pageSize);
    }

}