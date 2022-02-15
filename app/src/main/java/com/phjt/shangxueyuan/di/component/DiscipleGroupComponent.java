package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.DiscipleGroupModule;
import com.phjt.shangxueyuan.mvp.contract.DiscipleGroupContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.DiscipleGroupActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/03 17:45
 */

@ActivityScope
@Component(modules = DiscipleGroupModule.class, dependencies = AppComponent.class)
public interface DiscipleGroupComponent {
    void inject(DiscipleGroupActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DiscipleGroupComponent.Builder view(DiscipleGroupContract.View view);

        DiscipleGroupComponent.Builder appComponent(AppComponent appComponent);

        DiscipleGroupComponent build();
    }
}