package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.ArticleListContract;
import com.phjt.shangxueyuan.mvp.model.ArticleListModel;


/**
 * @author: Created by Template
 * date: 05/07/2020 17:42
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class ArticleListModule {

    @Binds
    abstract ArticleListContract.Model bindArticleListModel(ArticleListModel model);
}