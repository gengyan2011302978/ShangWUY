package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.LiveSharingModule;
import com.phjt.shangxueyuan.mvp.contract.LiveSharingContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.LiveSharingActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/04/07 17:27
 */

@ActivityScope
@Component(modules = LiveSharingModule.class, dependencies = AppComponent.class)
public interface LiveSharingComponent {
    void inject(LiveSharingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        LiveSharingComponent.Builder view(LiveSharingContract.View view);

        LiveSharingComponent.Builder appComponent(AppComponent appComponent);

        LiveSharingComponent build();
    }
}