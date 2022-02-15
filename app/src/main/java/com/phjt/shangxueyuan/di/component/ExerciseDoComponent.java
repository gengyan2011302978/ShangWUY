package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.ExerciseDoModule;
import com.phjt.shangxueyuan.mvp.contract.ExerciseDoContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.ExerciseDoActivity;


/**
 * @author: Created by GengYan
 * date: 03/16/2021 09:46
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = ExerciseDoModule.class, dependencies = AppComponent.class)
public interface ExerciseDoComponent {
    void inject(ExerciseDoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ExerciseDoComponent.Builder view(ExerciseDoContract.View view);

        ExerciseDoComponent.Builder appComponent(AppComponent appComponent);

        ExerciseDoComponent build();
    }
}