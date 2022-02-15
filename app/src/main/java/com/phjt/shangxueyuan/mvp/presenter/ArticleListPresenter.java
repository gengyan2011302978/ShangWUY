package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.ArticleClassifyBean;
import com.phjt.shangxueyuan.bean.ArticleListBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseCourseListBean;
import com.phjt.shangxueyuan.bean.CourseItemBean;
import com.phjt.shangxueyuan.mvp.contract.ArticleListContract;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by Template
 * date: 05/07/2020 17:42
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class ArticleListPresenter extends BasePresenter<ArticleListContract.Model, ArticleListContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public ArticleListPresenter(ArticleListContract.Model model, ArticleListContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    /**
     * 资讯列表
     */
    public void getArticleList(String id,int currentPage,int pageSize,boolean isRefresh) {
        mModel.getArticleList(id,currentPage,pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<ArticleListBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<ArticleListBean> baseBean) {
                        if (baseBean.isOk()) {
                            ArticleListBean baseListBean = baseBean.data;
                            if (baseListBean != null) {
                                Integer totalPage = baseListBean.getTotalPage();
                                List<ArticleListBean.RecordsBean> recordList = baseListBean.getRecords();

                                if (recordList == null || recordList.isEmpty()) {
                                    mRootView.showEmptyView();
                                }

                                if (isRefresh) {
                                    mRootView.showAuditionCourseRefresh(recordList);
                                    mRootView.canLoadMore();
                                } else {
                                    mRootView.showAuditionCourseLoadMore(recordList);
                                }
                                if (currentPage < totalPage) {
                                    mRootView.canLoadMore();
                                } else {
                                    mRootView.cannotLoadMore();
                                }
                            }
//                            mRootView.getArticleListSuccess(baseBean.data.getRecords());
                        } else {
                            if(Constant.LOGOUT_CODE_ERROR != baseBean.code){
                                mRootView.showMessage(baseBean.msg);
                            }
                        }
                    }
                });

    }
}
