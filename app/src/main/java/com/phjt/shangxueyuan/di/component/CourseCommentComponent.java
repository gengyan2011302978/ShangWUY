package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.CourseCommentModule;
import com.phjt.shangxueyuan.mvp.contract.CourseCommentContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.CourseCommentFragment;


/**
 * @author: Created by GengYan
 * date: 05/06/2020 11:32
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = CourseCommentModule.class, dependencies = AppComponent.class)
public interface CourseCommentComponent {
    void inject(CourseCommentFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CourseCommentComponent.Builder view(CourseCommentContract.View view);

        CourseCommentComponent.Builder appComponent(AppComponent appComponent);

        CourseCommentComponent build();
    }
}