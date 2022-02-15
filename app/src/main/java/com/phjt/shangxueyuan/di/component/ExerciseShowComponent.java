package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.ExerciseShowModule;
import com.phjt.shangxueyuan.mvp.contract.ExerciseShowContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.ExerciseShowActivity;


/**
 * @author: Created by GengYan
 * date: 03/11/2021 11:13
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = ExerciseShowModule.class, dependencies = AppComponent.class)
public interface ExerciseShowComponent {
    void inject(ExerciseShowActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ExerciseShowComponent.Builder view(ExerciseShowContract.View view);

        ExerciseShowComponent.Builder appComponent(AppComponent appComponent);

        ExerciseShowComponent build();
    }
}