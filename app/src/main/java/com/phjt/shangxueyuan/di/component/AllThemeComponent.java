package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.AllThemeModule;
import com.phjt.shangxueyuan.mvp.contract.AllThemeContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.AllThemeActivity;


/**
 * @author: Created by GengYan
 * date: 01/28/2021 16:23
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = AllThemeModule.class, dependencies = AppComponent.class)
public interface AllThemeComponent {
    void inject(AllThemeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AllThemeComponent.Builder view(AllThemeContract.View view);

        AllThemeComponent.Builder appComponent(AppComponent appComponent);

        AllThemeComponent build();
    }
}