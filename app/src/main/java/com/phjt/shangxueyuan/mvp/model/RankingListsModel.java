package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.IntegralRankingBean;
import com.phjt.shangxueyuan.bean.PointsDetailBean;
import com.phjt.shangxueyuan.mvp.contract.RankingListsContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 15:19
 */

@ActivityScope
public class RankingListsModel extends BaseModel implements RankingListsContract.Model {


    @Inject
    public RankingListsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
    @Override
    public Observable<BaseBean<List<IntegralRankingBean>>> getIntegralRanking() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getIntegralRanking();
    }
}