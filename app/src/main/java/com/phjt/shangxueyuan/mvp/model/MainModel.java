package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.shangxueyuan.bean.ActivityInfoBean;
import com.phjt.shangxueyuan.bean.AnnouncementListBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CouRecommendListBean;
import com.phjt.shangxueyuan.bean.CourseRecommendListBean;
import com.phjt.shangxueyuan.bean.HomeHotRecommendListBean;
import com.phjt.shangxueyuan.bean.InitIndexSiteInfoBean;
import com.phjt.shangxueyuan.bean.ListBannerBean;
import com.phjt.shangxueyuan.bean.PopularArticleListBean;
import com.phjt.shangxueyuan.bean.ProdInfoBean;
import com.phjt.shangxueyuan.bean.ShareImgBean;
import com.phjt.shangxueyuan.bean.UserWatchHistoryBean;
import com.phjt.shangxueyuan.mvp.contract.MainContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author: Created by Template
 * date: 2020/03/27 13:48
 * company: 普华集团
 * description: 描述
 */

@FragmentScope
public class MainModel extends BaseModel implements MainContract.Model {


    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<List<ListBannerBean>>> ListBanner(String bannerType) {
        if (bannerType.equals("12")){
            return mRepositoryManager.obtainRetrofitService(ApiService.class).ListBanner("image/v2.1.0/homeBanner",bannerType, 1);
        }
        return mRepositoryManager.obtainRetrofitService(ApiService.class).ListBanner("image/v1.0.0/listBanner",bannerType, 1);
    }

    @Override
    public Observable<BaseBean<List<PopularArticleListBean>>> getPopularArticle() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getPopularArticle();
    }

    @Override
    public Observable<BaseBean<List<CourseRecommendListBean>>> getCourseRecommend() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getCourseRecommend();
    }

    @Override
    public Observable<BaseBean<List<CourseRecommendListBean>>> getCourseCategory() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getCourseCategory();
    }
    @Override
    public Observable<BaseBean<List<CouRecommendListBean>>> getCouRecommend(int type) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getCouRecommend(type);
    }
    @Override
    public Observable<BaseBean<UserWatchHistoryBean>> getUserWatchHistory() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getUserWatchHistory();
    }

    @Override
    public Observable<BaseBean<ActivityInfoBean>> getActivityInfo() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getActivityInfo();
    }

    @Override
    public Observable<BaseBean<ProdInfoBean>> getSwyProd(String prodInfo) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getSwyProd(prodInfo);
    }

    @Override
    public Observable<BaseBean<List<InitIndexSiteInfoBean>>> getInitIndexSiteInfo() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getInitIndexSiteInfo();
    }

    @Override
    public Observable<BaseBean<List<HomeHotRecommendListBean>>> getHomeHotRecommend() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getHomeHotRecommend();
    }

    @Override
    public Observable<BaseBean<List<AnnouncementListBean>>> getAnnouncementList() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getAnnouncementList();
    }

    /**
     * 邀请有礼接口
     */
    @Override
    public Observable<BaseBean<List<ShareImgBean>>> inviteShare() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).inviteShare();
    }

    @Override
    public Observable<BaseBean<String>> answersState() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).answersState();
    }

    @Override
    public Observable<BaseBean> inviteShareT() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).inviteShareT();
    }
}