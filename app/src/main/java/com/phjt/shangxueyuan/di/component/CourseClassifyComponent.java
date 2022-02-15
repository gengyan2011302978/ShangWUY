package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.CourseClassifyModule;
import com.phjt.shangxueyuan.mvp.contract.CourseClassifyContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseClassifyActivity;


/**
 * @author: Created by GengYan
 * date: 05/06/2020 17:36
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = CourseClassifyModule.class, dependencies = AppComponent.class)
public interface CourseClassifyComponent {
    void inject(CourseClassifyActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CourseClassifyComponent.Builder view(CourseClassifyContract.View view);

        CourseClassifyComponent.Builder appComponent(AppComponent appComponent);

        CourseClassifyComponent build();
    }
}