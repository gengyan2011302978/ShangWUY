package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.ArticleClassifyModule;
import com.phjt.shangxueyuan.mvp.contract.ArticleClassifyContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.ArticleClassifyActivity;


/**
 * @author: Created by Template
 * date: 05/07/2020 17:26
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = ArticleClassifyModule.class, dependencies = AppComponent.class)
public interface ArticleClassifyComponent {
    void inject(ArticleClassifyActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ArticleClassifyComponent.Builder view(ArticleClassifyContract.View view);

        ArticleClassifyComponent.Builder appComponent(AppComponent appComponent);

        ArticleClassifyComponent build();
    }
}