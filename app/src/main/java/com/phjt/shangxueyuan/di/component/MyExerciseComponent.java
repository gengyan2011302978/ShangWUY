package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.MyExerciseModule;
import com.phjt.shangxueyuan.mvp.contract.MyExerciseContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.MyExerciseActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/03/11 13:54
 */

@ActivityScope
@Component(modules = MyExerciseModule.class, dependencies = AppComponent.class)
public interface MyExerciseComponent {
    void inject(MyExerciseActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyExerciseComponent.Builder view(MyExerciseContract.View view);

        MyExerciseComponent.Builder appComponent(AppComponent appComponent);

        MyExerciseComponent build();
    }
}