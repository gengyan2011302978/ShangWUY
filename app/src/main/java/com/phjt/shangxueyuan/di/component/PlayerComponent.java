package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.PlayerModule;
import com.phjt.shangxueyuan.mvp.contract.PlayerContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.PlayerActivity;


/**
 * @author: Created by Template
 * date: 03/25/2020 14:40
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = PlayerModule.class, dependencies = AppComponent.class)
public interface PlayerComponent {
    void inject(PlayerActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PlayerComponent.Builder view(PlayerContract.View view);

        PlayerComponent.Builder appComponent(AppComponent appComponent);

        PlayerComponent build();
    }
}