package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
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

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 2020/03/27 13:48
 * company: 普华集团
 * description: 描述
 */
public interface MainContract {
    /**
     * 对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
     */
    interface View extends IBaseView {
        void ListBannerSuccess(BaseBean<List<ListBannerBean>> bean, String type);

        void canLoadMore();

        void getAnnouncementListSuccess(List<AnnouncementListBean> bean);

        void getPopularArticleListSuccess(List<PopularArticleListBean> bean);

        void getCourseRecommendListSuccess(List<CourseRecommendListBean> bean);

        void getCourseCategoryListSuccess(List<CourseRecommendListBean> bean);
        void getCouRecommendSuccess(List<CouRecommendListBean> bean,int type);

        void getInitIndexSiteInfoListSuccess(List<InitIndexSiteInfoBean> bean);
        void getHomeHotRecommendSuccess(List<HomeHotRecommendListBean> bean);

        void getActivityInfoSuccess(ActivityInfoBean bean);

        void getUserWatchHistorySuccess(UserWatchHistoryBean bean);

        void getSwyProdSuccess(ProdInfoBean bean);

        void loadInviteSharetSuccess(String data);

        void loadInviteShareSuccess(BaseBean<List<ShareImgBean>> data, String url);
        void answersStateSuccess(BaseBean data);
    }

    /**
     * Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
     */
    interface Model extends IModel {
        Observable<BaseBean<List<ListBannerBean>>> ListBanner(String bannerType);

        Observable<BaseBean<List<PopularArticleListBean>>> getPopularArticle();

        Observable<BaseBean<List<CourseRecommendListBean>>> getCourseRecommend();

        Observable<BaseBean<List<CourseRecommendListBean>>> getCourseCategory();
        Observable<BaseBean<List<CouRecommendListBean>>> getCouRecommend(int type);

        Observable<BaseBean<UserWatchHistoryBean>> getUserWatchHistory();

        Observable<BaseBean<ActivityInfoBean>> getActivityInfo();

        Observable<BaseBean<ProdInfoBean>> getSwyProd(String prodInfo);

        Observable<BaseBean<List<InitIndexSiteInfoBean>>> getInitIndexSiteInfo();
        Observable<BaseBean<List<HomeHotRecommendListBean>>> getHomeHotRecommend();

        Observable<BaseBean<List<AnnouncementListBean>>> getAnnouncementList();

        /**
         * 邀请用户接口
         */
        Observable<BaseBean> inviteShareT();

        Observable<BaseBean<List<ShareImgBean>>> inviteShare();
        Observable<BaseBean<String>> answersState();


    }
}
