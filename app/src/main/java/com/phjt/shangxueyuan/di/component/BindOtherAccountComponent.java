package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.BindOtherAccountModule;
import com.phjt.shangxueyuan.mvp.contract.BindOtherAccountContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.BindOtherAccountActivity;


/**
 * @author: Created by Template
 * date: 12/28/2020 17:51
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = BindOtherAccountModule.class, dependencies = AppComponent.class)
public interface BindOtherAccountComponent {
    void inject(BindOtherAccountActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BindOtherAccountComponent.Builder view(BindOtherAccountContract.View view);

        BindOtherAccountComponent.Builder appComponent(AppComponent appComponent);

        BindOtherAccountComponent build();
    }
}