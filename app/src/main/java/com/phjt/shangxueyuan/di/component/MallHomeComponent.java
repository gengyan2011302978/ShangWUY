package com.phjt.shangxueyuan.di.component;

import com.phjt.base.di.component.AppComponent;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.di.module.MallHomeModule;
import com.phjt.shangxueyuan.mvp.contract.MallHomeContract;
import com.phjt.shangxueyuan.mvp.ui.fragment.MallHomeFragment;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 14:43
 * @description :
 */
@ActivityScope
@Component(modules = MallHomeModule.class, dependencies = AppComponent.class)
public interface MallHomeComponent {

    void inject(MallHomeFragment activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MallHomeComponent.Builder view(MallHomeContract.View view);

        MallHomeComponent.Builder appComponent(AppComponent appComponent);

        MallHomeComponent build();
    }
}