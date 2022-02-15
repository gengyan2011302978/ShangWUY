package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MotifDetailBean;
import com.phjt.shangxueyuan.bean.MotifDiaryListBean;
import com.phjt.shangxueyuan.mvp.contract.HistoryThemeContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;


/**
 * @author: Created by GengYan
 * date: 01/28/2021 17:08
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class HistoryThemePresenter extends BasePresenter<HistoryThemeContract.Model, HistoryThemeContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public HistoryThemePresenter(HistoryThemeContract.Model model, HistoryThemeContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    public void motifDetail(String id, String punchCardId) {
        mModel.motifDetail(id, punchCardId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<MotifDetailBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<MotifDetailBean> bean) {
                        if (bean.isOk()) {
                            mRootView.motifDetailSucceed(bean.data);
                        } else {
                            mRootView.showMessage(bean.msg);
                        }
                    }
                });
    }

    public void diaryList(String id, String type, String motifId) {
        mModel.diaryList(id, type, motifId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<MotifDiaryListBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<MotifDiaryListBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.diaryListSucceed(bean.data);
                        } else {
                            mRootView.showMessage(bean.msg);
                        }
                    }
                });
    }

    public void punchThumbsUp(String otherId, String otherType, String state) {
        mModel.punchThumbsUp(otherId, otherType)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.isOk()) {
                            mRootView.punchThumbsUpSucceed(state);
                        } else {
                            mRootView.punchThumbsUpFail(bean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.punchThumbsUpFail("网络异常");
                    }
                });
    }

    /**
     * 发布评论
     */
    public void addComment(String bundleAddDiaryId, String punchCardId, String motifId, String commentId, String mContent) {
        mModel.addComment(bundleAddDiaryId, punchCardId, motifId, commentId, mContent)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.addCommentSuccess();
                        } else {
                            mRootView.showMessage(integerBaseBean.msg);
                        }
                    }
                });
    }

}
