package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.ExerciseListModule;
import com.phjt.shangxueyuan.mvp.contract.ExerciseListContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.ExerciseListActivity;


/**
 * @author: Created by GengYan
 * date: 03/22/2021 16:26
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = ExerciseListModule.class, dependencies = AppComponent.class)
public interface ExerciseListComponent {
    void inject(ExerciseListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ExerciseListComponent.Builder view(ExerciseListContract.View view);

        ExerciseListComponent.Builder appComponent(AppComponent appComponent);

        ExerciseListComponent build();
    }
}