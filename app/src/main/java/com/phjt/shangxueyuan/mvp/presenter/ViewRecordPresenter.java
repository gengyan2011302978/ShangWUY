package com.phjt.shangxueyuan.mvp.presenter;

import android.annotation.SuppressLint;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ViewRecordBean;
import com.phjt.shangxueyuan.mvp.contract.ViewRecordContract;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;


/**
 * @author: Created by Template
 * date: 03/27/2020 17:38
 * company: 普华集团
 * description: 观看记录
 */
@ActivityScope
public class ViewRecordPresenter extends BasePresenter<ViewRecordContract.Model, ViewRecordContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public ViewRecordPresenter(ViewRecordContract.Model model, ViewRecordContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 购买课程，生成订单详情
     */
    @SuppressLint("CheckResult")
    public void getViewRecord(int pageNo, int pageSize, boolean isRefresh) {
        mModel.getViewRecord(pageNo, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<ViewRecordBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<ViewRecordBean> viewRecordBean) {
                        if (viewRecordBean.isOk()) {
                            ViewRecordBean bean = viewRecordBean.data;
                            int totalPage = bean.getTotalPage();
                            List<ViewRecordBean.RecordsBean> recordsBeanList = bean.getRecords();
                            if (isRefresh) {
                                if (recordsBeanList != null && recordsBeanList.size() > 0) {
                                    mRootView.showViewRecordRefresh(recordsBeanList);
                                    mRootView.canLoadMore();
                                } else if (recordsBeanList != null && recordsBeanList.size() == 0) {
                                    mRootView.showEmptyView();
                                } else {
                                    mRootView.showEmptyView();
                                }
                            } else {
                                mRootView.showViewRecordLoadMore(recordsBeanList);
                            }
                            if (pageNo >= totalPage) {
                                mRootView.cannotLoadMore();
                            } else {
                                mRootView.canLoadMore();
                            }
                        } else {
                            if(Constant.LOGOUT_CODE_ERROR != viewRecordBean.code){
                                mRootView.showMessage(viewRecordBean.msg);
                            }
                            mRootView.showEmptyView();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.showEmptyView();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}
