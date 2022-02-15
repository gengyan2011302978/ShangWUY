package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.CourseDetailIntroduceModule;
import com.phjt.shangxueyuan.mvp.contract.CourseIntroduceContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.CourseIntroduceFragment;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 15:49
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = CourseDetailIntroduceModule.class, dependencies = AppComponent.class)
public interface CourseDetailIntroduceComponent {
    void inject(CourseIntroduceFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CourseDetailIntroduceComponent.Builder view(CourseIntroduceContract.View view);

        CourseDetailIntroduceComponent.Builder appComponent(AppComponent appComponent);

        CourseDetailIntroduceComponent build();
    }
}