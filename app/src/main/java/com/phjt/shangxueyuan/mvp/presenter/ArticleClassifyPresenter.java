package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.ActivityInfoBean;
import com.phjt.shangxueyuan.bean.ArticleClassifyBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.ArticleClassifyContract;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by Template
 * date: 05/07/2020 17:26
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class ArticleClassifyPresenter extends BasePresenter<ArticleClassifyContract.Model, ArticleClassifyContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public ArticleClassifyPresenter(ArticleClassifyContract.Model model, ArticleClassifyContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    /**
     * 资讯分类
     */
    public void getArticleClassify() {
        mModel.getArticleClassify()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<ArticleClassifyBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<ArticleClassifyBean>> baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.getArticleClassifySuccess(baseBean.data);
                        } else {
                            if(Constant.LOGOUT_CODE_ERROR != baseBean.code){
                                mRootView.showMessage(baseBean.msg);
                            }
                        }
                    }
                });

    }
}
