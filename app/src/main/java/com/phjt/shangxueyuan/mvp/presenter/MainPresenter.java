package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
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
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * @author: Created by Template
 * date: 2020/03/27 13:48
 * company: 普华集团
 * description: 描述
 */

@FragmentScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mErrorHandler = null;
    }

    /**
     * 首页banner
     */
    public void ListBanner(String bannerType) {
        mModel.ListBanner(bannerType)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<ListBannerBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<ListBannerBean>> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.ListBannerSuccess(baseBean, bannerType);
                            mRootView.canLoadMore();
                        } else {
                            if (Constant.LOGOUT_CODE_ERROR != baseBean.code &&
                                    baseBean.code != 90003) {
                                mRootView.showMessage(baseBean.msg);
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mRootView.canLoadMore();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.canLoadMore();
                    }
                });

    }

    /**
     * 首页公告
     */
    public void getAnnouncementList() {
        mModel.getAnnouncementList()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<AnnouncementListBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<AnnouncementListBean>> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.getAnnouncementListSuccess(baseBean.data);
                        } else {
                            if (Constant.LOGOUT_CODE_ERROR != baseBean.code) {
                                mRootView.showMessage(baseBean.msg);
                            }
                        }
                    }
                });

    }

    /**
     * 首页资讯
     */
    public void getPopularArticle() {
        mModel.getPopularArticle()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<PopularArticleListBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<PopularArticleListBean>> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.getPopularArticleListSuccess(baseBean.data);
                        } else {
                            if (Constant.LOGOUT_CODE_ERROR != baseBean.code) {
                                mRootView.showMessage(baseBean.msg);
                            }
                        }
                    }
                });

    }

    /**
     * 首页课程推荐
     */
    public void getCourseRecommend() {
        mModel.getCourseRecommend()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<CourseRecommendListBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<CourseRecommendListBean>> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.getCourseRecommendListSuccess(baseBean.data);
                        } else {
                            if (Constant.LOGOUT_CODE_ERROR != baseBean.code) {
                                mRootView.showMessage(baseBean.msg);
                            }
                        }
                    }
                });

    }

    /**
     * 首页直播、数字经济等课程推荐
     */
    public void getCourseCategory() {
        mModel.getCourseCategory()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<CourseRecommendListBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<CourseRecommendListBean>> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.getCourseCategoryListSuccess(baseBean.data);
                        } else {
                            if (Constant.LOGOUT_CODE_ERROR != baseBean.code) {
                                mRootView.showMessage(baseBean.msg);
                            }
                        }
                    }
                });
    }

    /**
     * 首页直播、数字经济等课程推荐
     */
    public void getCouRecommend(int type) {
        mModel.getCouRecommend(type)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<CouRecommendListBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<CouRecommendListBean>> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.getCouRecommendSuccess(baseBean.data, type);
                        } else {
                            if (Constant.LOGOUT_CODE_ERROR != baseBean.code) {
                                mRootView.showMessage(baseBean.msg);
                            }
                        }
                    }
                });
    }

    /**
     * 首页最近观看记录接口
     */
    public void getUserWatchHistory() {
        mModel.getUserWatchHistory()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<UserWatchHistoryBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<UserWatchHistoryBean> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.getUserWatchHistorySuccess(baseBean.data);
                        } else {
                            if (Constant.LOGOUT_CODE_ERROR != baseBean.code) {
                                mRootView.showMessage(baseBean.msg);
                            }
                        }
                    }
                });

    }

    /**
     * 首页活动展示接口
     */
    public void getActivityInfo() {
        mModel.getActivityInfo()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<ActivityInfoBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<ActivityInfoBean> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.getActivityInfoSuccess(baseBean.data);
                        } else {
                            if (Constant.LOGOUT_CODE_ERROR != baseBean.code) {
                                mRootView.showMessage(baseBean.msg);
                            }
                        }
                    }
                });

    }

    /**
     * 微信号获取
     */
    public void getSwyProd(String prodInfo) {
        mModel.getSwyProd(prodInfo)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<ProdInfoBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<ProdInfoBean> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.getSwyProdSuccess(baseBean.data);
                        } else {
                            if (Constant.LOGOUT_CODE_ERROR != baseBean.code) {
                                //mRootView.showMessage(baseBean.msg);
                            }
                        }
                    }
                });

    }

    /**
     * 首页栏目各位置查询
     */
    public void getInitIndexSiteInfo() {
        mModel.getInitIndexSiteInfo()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<InitIndexSiteInfoBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<InitIndexSiteInfoBean>> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.getInitIndexSiteInfoListSuccess(baseBean.data);
                        } else {
                            if (Constant.LOGOUT_CODE_ERROR != baseBean.code) {
                                mRootView.showMessage(baseBean.msg);
                            }
                        }
                    }
                });
    }

    /**
     * 首页-热门推荐
     */
    public void getHomeHotRecommend() {
        mModel.getHomeHotRecommend()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<HomeHotRecommendListBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<HomeHotRecommendListBean>> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.getHomeHotRecommendSuccess(baseBean.data);
                        } else {
                            if (Constant.LOGOUT_CODE_ERROR != baseBean.code) {
                                mRootView.showMessage(baseBean.msg);
                            }
                        }
                    }
                });
    }

    /**
     * 邀请请有礼接口一
     */
    public void inviteShareT() {
        mModel.inviteShareT()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.isOk()) {
                            mRootView.loadInviteSharetSuccess(String.valueOf(bean.data));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     * 邀请请有礼接口二
     */
    public void inviteShare(String url) {
        mModel.inviteShare()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<ShareImgBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<ShareImgBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.loadInviteShareSuccess(bean, url);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     * 来问答
     */
    public void answersState() {
        mModel.answersState()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<String> bean) {
                        if (bean.isOk()) {
                            mRootView.answersStateSuccess(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
