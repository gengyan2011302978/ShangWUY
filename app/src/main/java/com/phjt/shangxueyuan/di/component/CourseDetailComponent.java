package com.phjt.shangxueyuan.di.component;

import com.phjt.base.di.component.AppComponent;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.di.module.CourseDetailModule;
import com.phjt.shangxueyuan.mvp.contract.CourseDetailContract;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 14:34
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = CourseDetailModule.class, dependencies = AppComponent.class)
public interface CourseDetailComponent {
    void inject(CourseDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CourseDetailComponent.Builder view(CourseDetailContract.View view);

        CourseDetailComponent.Builder appComponent(AppComponent appComponent);

        CourseDetailComponent build();
    }
}