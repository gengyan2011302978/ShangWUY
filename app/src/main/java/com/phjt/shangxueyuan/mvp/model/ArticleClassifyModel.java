package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.ArticleClassifyBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.ArticleClassifyContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 05/07/2020 17:26
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class ArticleClassifyModel extends BaseModel implements ArticleClassifyContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public ArticleClassifyModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<List<ArticleClassifyBean>>> getArticleClassify() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getArticleClassify();
    }
}