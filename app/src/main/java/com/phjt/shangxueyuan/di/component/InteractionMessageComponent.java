package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.InteractionMessageModule;
import com.phjt.shangxueyuan.mvp.contract.InteractionMessageContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.InteractionMessageActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/20 13:54
 */

@ActivityScope
@Component(modules = InteractionMessageModule.class, dependencies = AppComponent.class)
public interface InteractionMessageComponent {
    void inject(InteractionMessageActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        InteractionMessageComponent.Builder view(InteractionMessageContract.View view);

        InteractionMessageComponent.Builder appComponent(AppComponent appComponent);

        InteractionMessageComponent build();
    }
}