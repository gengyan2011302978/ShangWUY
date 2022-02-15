package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;


import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ExpirationNoticeBean;
import com.phjt.shangxueyuan.bean.ListBannerBean;
import com.phjt.shangxueyuan.bean.MainAnnouncementBean;
import com.phjt.shangxueyuan.bean.UpdateAppBean;
import com.phjt.shangxueyuan.mvp.contract.HomePagerContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 03/24/2020 14:09
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class HomePagerModel extends BaseModel implements HomePagerContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public HomePagerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<Integer>> getUnReadCount() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getUnReadCount();
    }

    @Override
    public Observable<BaseBean<UpdateAppBean>> getCheckVersion() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getCheckVersion(1);
    }

    @Override
    public Observable<BaseBean<MainAnnouncementBean>> getAnnouncementJson() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getAnnouncementJson(BuildConfig.HOST_URL_NOTICE);
    }

    @Override
    public Observable<BaseBean<List<ListBannerBean>>> getActivityBanner(String bannerType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).ListBanner("image/v1.0.0/listBanner",bannerType, 1);
    }

    @Override
    public Observable<BaseBean> getBannerTips() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getBannerTips();
    }

    /**
     * 优惠券到期通知
     */
    @Override
    public Observable<BaseBean<ExpirationNoticeBean>> getExpirationNotice() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getExpirationNotice();
    }

    @Override
    public Observable<BaseBean<Boolean>> getBindStatus(String planetMobile, String mobile) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).checkBindStatus(planetMobile, mobile);
    }
}