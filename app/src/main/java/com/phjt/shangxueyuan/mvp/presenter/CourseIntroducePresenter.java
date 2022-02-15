package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.CourseCommentBean;
import com.phjt.shangxueyuan.bean.CourseCommentSizeBean;
import com.phjt.shangxueyuan.bean.CourseDetailBean;
import com.phjt.shangxueyuan.mvp.contract.CourseIntroduceContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 15:49
 * company: 普华集团
 * description: 课程介绍
 */
@FragmentScope
public class CourseIntroducePresenter extends BasePresenter<CourseIntroduceContract.Model, CourseIntroduceContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public CourseIntroducePresenter(CourseIntroduceContract.Model model, CourseIntroduceContract.View rootView) {
        super(model, rootView);
    }

    public void getCourseCommentList(String courseId, String commentType, int currentPage, int pageSize, boolean isRefresh) {
        mModel.getCourseCommentList(courseId, commentType, currentPage, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<CourseCommentBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<CourseCommentBean>> baseBean) {
                        if (baseBean.isOk()) {
                            BaseListBean<CourseCommentBean> baseListBean = baseBean.data;
                            if (baseListBean != null) {
                                int totalPage = baseListBean.getTotalPage();
                                List<CourseCommentBean> commentList = baseListBean.getRecords();

                                if (isRefresh) {
                                    if (commentList != null && !commentList.isEmpty()) {
                                        mRootView.showCommentListRefresh(commentList);
                                        mRootView.canLoadMore();
                                    } else {
                                        mRootView.showCommentListRefresh(null);
                                        mRootView.showEmptyView();
                                    }
                                } else {
                                    mRootView.showCommentListLoadMore(commentList);
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

    public void commentZanState(CourseCommentBean commentBean, int status, int position) {
        mModel.commentZanState(commentBean.getId(), status)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.zanStateSuccess(commentBean, position, status);
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    public void getCourseCommentSize(String courseId) {
        mModel.getCourseCommentSize(courseId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<CourseCommentSizeBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<CourseCommentSizeBean> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.getCourseCommentSizeSuccess(baseBean.data);
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
