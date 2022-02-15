package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.MineExerciseModule;
import com.phjt.shangxueyuan.mvp.contract.MineExerciseContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.MineExerciseFragment;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/03/17 10:24
 */

@FragmentScope
@Component(modules = MineExerciseModule.class, dependencies = AppComponent.class)
public interface MineExerciseComponent {
    void inject(MineExerciseFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MineExerciseComponent.Builder view(MineExerciseContract.View view);

        MineExerciseComponent.Builder appComponent(AppComponent appComponent);

        MineExerciseComponent build();
    }
}