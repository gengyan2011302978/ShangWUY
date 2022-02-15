package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseCourseListBean;
import com.phjt.shangxueyuan.bean.CourseCatalogFirstBean;
import com.phjt.shangxueyuan.mvp.contract.CourseCatalogContract;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 15:52
 * company: 普华集团
 * description: 课程目录
 */
@FragmentScope
public class CourseCatalogPresenter extends BasePresenter<CourseCatalogContract.Model, CourseCatalogContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public CourseCatalogPresenter(CourseCatalogContract.Model model, CourseCatalogContract.View rootView) {
        super(model, rootView);
    }

    public void getCourseCalalogList(String courseId) {
//        mModel.getCourseCalalogList(courseId)
//                .compose(RxUtils.applySchedulersWithLoading(mRootView))
//                .subscribe(new ErrorHandleSubscriber<BaseBean<List<CourseCatalogFirstBean>>>(mErrorHandler) {
//                    @Override
//                    public void onNext(BaseBean<List<CourseCatalogFirstBean>> baseBean) {
//                        if (baseBean.isOk()) {
//                            List<CourseCatalogFirstBean> catalogList = baseBean.data;
//                            if (catalogList != null && !catalogList.isEmpty()) {
//                                mRootView.showCourseCatalog(catalogList);
//                            } else {
//                                mRootView.showEmptyView();
//                            }
//                        } else {
//                            if (Constant.LOGOUT_CODE_ERROR != baseBean.code) {
//                                mRootView.showMessage(baseBean.msg);
//                            }
//                        }
//                    }
//                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}
