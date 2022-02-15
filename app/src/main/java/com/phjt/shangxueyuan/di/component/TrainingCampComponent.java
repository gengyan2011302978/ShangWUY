package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.TrainingCampModule;
import com.phjt.shangxueyuan.mvp.contract.TrainingCampContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.TrainingCampFragment;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:14
 */

@FragmentScope
@Component(modules = TrainingCampModule.class, dependencies = AppComponent.class)
public interface TrainingCampComponent {
    void inject(TrainingCampFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TrainingCampComponent.Builder view(TrainingCampContract.View view);

        TrainingCampComponent.Builder appComponent(AppComponent appComponent);

        TrainingCampComponent build();
    }
}