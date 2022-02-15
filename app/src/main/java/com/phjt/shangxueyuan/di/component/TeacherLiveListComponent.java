package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.TeacherLiveListModule;
import com.phjt.shangxueyuan.mvp.contract.TeacherLiveListContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.TeacherLiveListActivity;


/**
 * @author: Created by GengYan
 * date: 04/14/2021 13:50
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = TeacherLiveListModule.class, dependencies = AppComponent.class)
public interface TeacherLiveListComponent {
    void inject(TeacherLiveListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TeacherLiveListComponent.Builder view(TeacherLiveListContract.View view);

        TeacherLiveListComponent.Builder appComponent(AppComponent appComponent);

        TeacherLiveListComponent build();
    }
}