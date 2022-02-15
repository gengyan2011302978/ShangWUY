package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.HomePagerModule;
import com.phjt.shangxueyuan.mvp.contract.HomePagerContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.HomePagerActivity;


/**
 * @author: Created by Template
 * date: 03/24/2020 14:09
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = HomePagerModule.class, dependencies = AppComponent.class)
public interface HomePagerComponent {
    void inject(HomePagerActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomePagerComponent.Builder view(HomePagerContract.View view);

        HomePagerComponent.Builder appComponent(AppComponent appComponent);

        HomePagerComponent build();
    }
}