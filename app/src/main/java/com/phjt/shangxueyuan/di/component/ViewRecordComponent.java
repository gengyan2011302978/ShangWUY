package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.ViewRecordModule;
import com.phjt.shangxueyuan.mvp.contract.ViewRecordContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.ViewRecordActivity;


/**
 * @author: Created by Template
 * date: 03/27/2020 17:38
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = ViewRecordModule.class, dependencies = AppComponent.class)
public interface ViewRecordComponent {
    void inject(ViewRecordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ViewRecordComponent.Builder view(ViewRecordContract.View view);

        ViewRecordComponent.Builder appComponent(AppComponent appComponent);

        ViewRecordComponent build();
    }
}