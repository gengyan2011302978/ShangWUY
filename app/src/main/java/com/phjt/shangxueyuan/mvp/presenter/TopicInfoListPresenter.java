package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.bean.TopicItemInfoBean;
import com.phjt.shangxueyuan.bean.TopicListBean;
import com.phjt.shangxueyuan.mvp.contract.TopicInfoListContract;
import com.phjt.shangxueyuan.utils.RxUtils;


/**
 * @author: Created by GengYan
 * date: 11/03/2020 18:23
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class TopicInfoListPresenter extends BasePresenter<TopicInfoListContract.Model, TopicInfoListContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public TopicInfoListPresenter(TopicInfoListContract.Model model, TopicInfoListContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    public void themeList(int pageNo, int pageSize, String type, String topicId, String topicUserId, boolean isRefresh) {
        mModel.themeList(pageNo, pageSize, type, topicId, topicUserId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<TopicItemInfoBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<TopicItemInfoBean> bean) {
                        if (bean.isOk()) {
                            mRootView.themeListSuccess(bean.data, pageNo, isRefresh);
                        } else {
                            mRootView.themeListFailure();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.themeListFailure();
                    }
                });
    }

    public void getShareItemData(int type, String otherId, String content) {
        mModel.getShareItemData(type, otherId, content)
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

    public void themeLike(String themeId, int position, String likestatus) {
        mModel.themeLike(themeId)
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
                    }
                });
    }

    public void themeDelete(String themeId, String topId, String topUserId) {
        mModel.themeDelete(themeId, topId, topUserId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.themeDeleteSuccess();
                        } else {
                            mRootView.themeDeleteFaile(baseBean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void themeTop(String themeId, String isTop, String topId, String topUserId) {
        mModel.themeTop(themeId, isTop, topId, topUserId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.themeTopSuccess();
                        } else {
                            mRootView.themeTopFaile(baseBean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
