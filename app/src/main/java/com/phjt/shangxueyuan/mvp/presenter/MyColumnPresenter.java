package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MessageBean;
import com.phjt.shangxueyuan.bean.ViewColumnBean;
import com.phjt.shangxueyuan.bean.ViewRecordBean;
import com.phjt.shangxueyuan.mvp.contract.MyColumnContract;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 12/09/2020 17:27
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class MyColumnPresenter extends BasePresenter<MyColumnContract.Model, MyColumnContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public MyColumnPresenter(MyColumnContract.Model model, MyColumnContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    public void getMyColumn(int pageNo, int pageSize, boolean isRefresh) {
        mModel.getMyColumn(pageNo, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<ViewColumnBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<ViewColumnBean> bean) {
                        if (bean.isOk()) {
                            ViewColumnBean viewColumnBean = bean.data;
                            int totalPage = viewColumnBean.getTotalPage();
                            List<ViewColumnBean.RecordsBean> recordsBeanList = viewColumnBean.getRecords();
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
                            if(Constant.LOGOUT_CODE_ERROR != bean.code){
                                mRootView.showMessage(bean.msg);
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

}
