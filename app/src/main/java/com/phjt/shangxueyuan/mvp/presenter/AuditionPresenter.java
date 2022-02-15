package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseCourseListBean;
import com.phjt.shangxueyuan.bean.CourseItemBean;
import com.phjt.shangxueyuan.bean.CourseTeacherItemBean;
import com.phjt.shangxueyuan.bean.CourseTypeItemBean;
import com.phjt.shangxueyuan.bean.LiveBean;
import com.phjt.shangxueyuan.mvp.contract.AuditionContract;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 10:49
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class AuditionPresenter extends BasePresenter<AuditionContract.Model, AuditionContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public AuditionPresenter(AuditionContract.Model model, AuditionContract.View rootView) {
        super(model, rootView);
    }

    public void getCourseList(int level, String couTypeId, Integer sort, String lecturerId, int currentPage, int pageSize, boolean isRefresh) {
        mModel.getCourseList(level, couTypeId, sort, lecturerId, currentPage, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseCourseListBean<CourseItemBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseCourseListBean<CourseItemBean>> baseBean) {
                        if (baseBean.isOk()) {
                            BaseCourseListBean<CourseItemBean> baseListBean = baseBean.data;
                            if (baseListBean != null) {
                                Integer totalPage = baseListBean.getTotalPage();
                                List<CourseItemBean> recordList = baseListBean.getRecords();

                                if (isRefresh) {
                                    mRootView.showAuditionCourseRefresh(recordList);

                                    if (recordList == null || recordList.isEmpty()) {
                                        mRootView.showEmptyView();
                                    } else {
                                        mRootView.canLoadMore();
                                    }
                                } else {
                                    mRootView.showAuditionCourseLoadMore(recordList);
                                }
                                if (currentPage < totalPage) {
                                    mRootView.canLoadMore();
                                } else {
                                    mRootView.cannotLoadMore();
                                }
                            }
                        } else {
                            if (Constant.LOGOUT_CODE_ERROR != baseBean.code) {
                                mRootView.showMessage(baseBean.msg);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.showEmptyView();
                    }
                });
    }

    public void getCourseTeacherList(String typeId, String lastTypeId) {
        mModel.getCourseTeacherList(typeId, lastTypeId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<CourseTeacherItemBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<CourseTeacherItemBean>> baseBean) {
                        if (baseBean.isOk()) {
                            List<CourseTeacherItemBean> courseTeacherList = baseBean.data;
                            if (courseTeacherList != null && !courseTeacherList.isEmpty()) {
                                mRootView.showCourseTeacherPop(courseTeacherList);
                            } else {
                                mRootView.showMessage("讲师列表为空");
                            }
                        } else {
                            if (Constant.LOGOUT_CODE_ERROR != baseBean.code) {
                                mRootView.showMessage(baseBean.msg);
                            }
                        }
                    }
                });
    }

    public void getStudentLiveList() {
        mModel.getStudentLiveList()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<LiveBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<LiveBean>> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.getStudentLiveListSuccess(baseBean.data);
                        } else {

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
