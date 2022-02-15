package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.BocTypeModule;
import com.phjt.shangxueyuan.mvp.contract.BocTypeContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.BocTypeActivity;


/**
 * @author: Created by GengYan
 * date: 10/27/2020 13:53
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = BocTypeModule.class, dependencies = AppComponent.class)
public interface BocTypeComponent {
    void inject(BocTypeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BocTypeComponent.Builder view(BocTypeContract.View view);

        BocTypeComponent.Builder appComponent(AppComponent appComponent);

        BocTypeComponent build();
    }
}