package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ExchangeCodeBean;
import com.phjt.shangxueyuan.bean.ListBannerBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.bean.ThemeMainBean;
import com.phjt.shangxueyuan.bean.TopicMainBean;
import com.phjt.shangxueyuan.mvp.contract.CircleContract;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 10/19/2020 10:24
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class CirclePresenter extends BasePresenter<CircleContract.Model, CircleContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public CirclePresenter(CircleContract.Model model, CircleContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    public void recommendTopics() {
        mModel.recommendTopics()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<TopicMainBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<TopicMainBean>> baseBean) {
                        if (baseBean.isOk()) {
                            List<TopicMainBean> catalogList = baseBean.data;
                            mRootView.recommendTopicsSuccess(catalogList);
                        } else {
                            mRootView.recommendTopicsFaile(baseBean.msg);
                        }
                    }
                });
    }

    public void getShareItemData(int type, String otherId, String content, int shareType, String couType) {
        mModel.getShareItemData(type, otherId, content, shareType, couType)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<ShareItemBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<ShareItemBean> baseBean) {
                        if (baseBean.isOk()) {
                            ShareItemBean shareItemBean = baseBean.data;
                            if (shareItemBean != null) {
                                mRootView.showShareItemDialog(shareItemBean);
                            } else {
                                mRootView.showMessage("分享数据为空");
                            }
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    public void addTopic(int type) {
        mModel.addTopic(type)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<String> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.addTopicSuccess(baseBean.data);
                        } else {
                            mRootView.addTopicFaile(baseBean.msg);
                        }
                    }
                });
    }

    public void themeLike(String themeSource, String themeId, int position, String likestatus, String commentId, String notesId, String leaveId, String courseId) {
        mModel.themeLike(themeSource, themeId, likestatus, commentId, notesId, leaveId, courseId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.themeLikeSuccess(position, likestatus);
                        } else {
                            mRootView.themeLikeFaile(baseBean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.canLoadMore();
                    }
                });
    }

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
                            if (Constant.LOGOUT_CODE_ERROR != baseBean.code) {
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

    public void getRefreshList(String themeId, int pageNo, int pageSize, boolean isRefresh, int position) {
        mModel.getRefreshList(themeId, pageNo, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<ThemeMainBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<ThemeMainBean> bean) {
                        if (bean.isOk()) {
                            mRootView.loadRefreshDataSuccess(bean.data, pageNo, isRefresh, position, themeId);
                        } else {
                            mRootView.loadRefreshDataFailure();
                            mRootView.canLoadMore();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadRefreshDataFailure();
                        mRootView.canLoadMore();
                    }
                });
    }
}
