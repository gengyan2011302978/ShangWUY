package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.ArticleListModule;
import com.phjt.shangxueyuan.mvp.contract.ArticleListContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.ArticleListFragment;


/**
 * @author: Created by Template
 * date: 05/07/2020 17:42
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = ArticleListModule.class, dependencies = AppComponent.class)
public interface ArticleListComponent {
    void inject(ArticleListFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ArticleListComponent.Builder view(ArticleListContract.View view);

        ArticleListComponent.Builder appComponent(AppComponent appComponent);

        ArticleListComponent build();
    }
}