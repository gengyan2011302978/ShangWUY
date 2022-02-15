package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.CourseCategoryModule;
import com.phjt.shangxueyuan.mvp.contract.CourseCategoryContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseCategoryActivity;


/**
 * @author: Created by GengYan
 * date: 05/06/2020 13:55
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = CourseCategoryModule.class, dependencies = AppComponent.class)
public interface CourseCategoryComponent {
    void inject(CourseCategoryActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CourseCategoryComponent.Builder view(CourseCategoryContract.View view);

        CourseCategoryComponent.Builder appComponent(AppComponent appComponent);

        CourseCategoryComponent build();
    }
}