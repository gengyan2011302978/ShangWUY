package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.ArticleClassifyContract;
import com.phjt.shangxueyuan.mvp.model.ArticleClassifyModel;


/**
 * @author: Created by Template
 * date: 05/07/2020 17:26
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class ArticleClassifyModule {

    @Binds
    abstract ArticleClassifyContract.Model bindArticleClassifyModel(ArticleClassifyModel model);
}