package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseCourseListBean;
import com.phjt.shangxueyuan.bean.DataBean;
import com.phjt.shangxueyuan.mvp.contract.DataFragmentContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 06/05/2020 18:12
 * company: 普华集团
 * description: 课程——资料页面
 */
@FragmentScope
public class DataFragmentPresenter extends BasePresenter<DataFragmentContract.Model, DataFragmentContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public DataFragmentPresenter(DataFragmentContract.Model model, DataFragmentContract.View rootView) {
        super(model, rootView);
    }

    public void getDataList(String courseId, int currentPage, int pageSize, boolean isRefresh, String courseType) {
        mModel.getDataList(courseId, currentPage, pageSize, courseType)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseCourseListBean<DataBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseCourseListBean<DataBean>> baseBean) {
                        if (baseBean.isOk()) {
                            BaseCourseListBean<DataBean> baseListBean = baseBean.data;
                            if (baseListBean != null) {
                                int totalPage = baseListBean.getTotalPage();
                                List<DataBean> dataBeanList = baseListBean.getRecords();

                                if (isRefresh) {
                                    mRootView.showDataListRefresh(dataBeanList);
                                    mRootView.showDataCount(baseListBean.getTotalCount());
                                } else {
                                    mRootView.showDataListLoadMore(dataBeanList);
                                }

                                if (currentPage < totalPage) {
                                    mRootView.canLoadMore();
                                } else {
                                    mRootView.cannotLoadMore();
                                }
                            }
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
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
