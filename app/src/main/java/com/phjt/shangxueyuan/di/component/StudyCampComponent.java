package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.StudyCampModule;
import com.phjt.shangxueyuan.mvp.contract.StudyCampContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.StudyCampFragment;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/04/23 09:41
 */

@ActivityScope
@Component(modules = StudyCampModule.class, dependencies = AppComponent.class)
public interface StudyCampComponent {
    void inject(StudyCampFragment activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        StudyCampComponent.Builder view(StudyCampContract.View view);

        StudyCampComponent.Builder appComponent(AppComponent appComponent);

        StudyCampComponent build();
    }
}