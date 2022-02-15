package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.CourseCatalogModule;
import com.phjt.shangxueyuan.mvp.contract.CourseCatalogContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.CourseCatalogFragment;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 15:52
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = CourseCatalogModule.class, dependencies = AppComponent.class)
public interface CourseCatalogComponent {
    void inject(CourseCatalogFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CourseCatalogComponent.Builder view(CourseCatalogContract.View view);

        CourseCatalogComponent.Builder appComponent(AppComponent appComponent);

        CourseCatalogComponent build();
    }
}