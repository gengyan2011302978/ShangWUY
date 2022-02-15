package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.ArticleListBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.ArticleListContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 05/07/2020 17:42
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class ArticleListModel extends BaseModel implements ArticleListContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public ArticleListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<ArticleListBean>> getArticleList(String id,int currentPage,int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getArticleList(id,currentPage,pageSize);
    }
}