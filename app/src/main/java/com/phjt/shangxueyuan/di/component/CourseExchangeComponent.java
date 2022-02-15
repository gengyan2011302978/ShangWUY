package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.CourseExchangeModule;
import com.phjt.shangxueyuan.mvp.contract.CourseExchangeContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.CourseExchangeFragment;


/**
 * @author: Created by GengYan
 * date: 08/06/2020 09:48
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = CourseExchangeModule.class, dependencies = AppComponent.class)
public interface CourseExchangeComponent {
    void inject(CourseExchangeFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CourseExchangeComponent.Builder view(CourseExchangeContract.View view);

        CourseExchangeComponent.Builder appComponent(AppComponent appComponent);

        CourseExchangeComponent build();
    }
}