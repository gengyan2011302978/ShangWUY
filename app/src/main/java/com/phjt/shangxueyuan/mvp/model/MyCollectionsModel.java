package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyCollectionBean;
import com.phjt.shangxueyuan.mvp.contract.MyCollectionsContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/08/26 09:53
 */

@FragmentScope
public class MyCollectionsModel extends BaseModel implements MyCollectionsContract.Model {


    @Inject
    public MyCollectionsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    /**
     * *获取课程收藏列表
     */
    @Override
    public Observable<BaseBean<BaseListBean<MyCollectionBean>>> getCollectionList(int type,int pageNo, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getCollectionList(type,pageNo, pageSize);
    }

    /**
     * *获取专题收藏记录列表
     */
    @Override
    public Observable<BaseBean<BaseListBean<MyCollectionBean>>> getSpecialFavouriteList(int pageNo, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getSpecialFavouriteList(pageNo, pageSize);
    }

    /**
     * 收藏编辑
     */
    @Override
    public Observable<BaseBean> getFavoriteEdit(String ids, int mType) {
        if (mType == 0||mType == 2 ) {
            return mRepositoryManager.obtainRetrofitService(ApiService.class).getFavoriteEdit(ids);
        } else {
            return mRepositoryManager.obtainRetrofitService(ApiService.class).getspecialEdit(ids);
        }
    }

}