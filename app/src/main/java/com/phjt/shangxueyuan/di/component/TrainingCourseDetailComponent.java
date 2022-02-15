package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.TrainingCourseDetailModule;
import com.phjt.shangxueyuan.mvp.contract.TrainingCourseDetailContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.TrainingCourseDetailFragment;


/**
 * @author: Created by GengYan
 * date: 01/19/2021 10:30
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = TrainingCourseDetailModule.class, dependencies = AppComponent.class)
public interface TrainingCourseDetailComponent {
    void inject(TrainingCourseDetailFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TrainingCourseDetailComponent.Builder view(TrainingCourseDetailContract.View view);

        TrainingCourseDetailComponent.Builder appComponent(AppComponent appComponent);

        TrainingCourseDetailComponent build();
    }
}