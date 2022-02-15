package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.di.module.AllStudyCampModule;
import com.phjt.shangxueyuan.mvp.contract.AllStudyCampContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.AllStudyCampActivity;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/08/19 17:12
 * @description :
 */
@ActivityScope
@Component(modules = AllStudyCampModule.class, dependencies = AppComponent.class)
public interface AllStudyCampComponent {

    void inject(AllStudyCampActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AllStudyCampComponent.Builder view(AllStudyCampContract.View view);

        AllStudyCampComponent.Builder appComponent(AppComponent appComponent);

        AllStudyCampComponent build();
    }
}