package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.ShareModule;
import com.phjt.shangxueyuan.mvp.contract.ShareContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.ShareActivity;


/**
 * @author: Created by GengYan
 * date: 07/07/2020 17:32
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = ShareModule.class, dependencies = AppComponent.class)
public interface ShareComponent {
    void inject(ShareActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ShareComponent.Builder view(ShareContract.View view);

        ShareComponent.Builder appComponent(AppComponent appComponent);

        ShareComponent build();
    }
}