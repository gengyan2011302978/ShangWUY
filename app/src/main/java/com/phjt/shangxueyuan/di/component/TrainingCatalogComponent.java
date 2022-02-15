package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.TrainingCatalogModule;
import com.phjt.shangxueyuan.mvp.contract.TrainingCatalogContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.TrainingCatalogFragment;


/**
 * @author: Created by GengYan
 * date: 01/19/2021 16:26
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = TrainingCatalogModule.class, dependencies = AppComponent.class)
public interface TrainingCatalogComponent {
    void inject(TrainingCatalogFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TrainingCatalogComponent.Builder view(TrainingCatalogContract.View view);

        TrainingCatalogComponent.Builder appComponent(AppComponent appComponent);

        TrainingCatalogComponent build();
    }
}