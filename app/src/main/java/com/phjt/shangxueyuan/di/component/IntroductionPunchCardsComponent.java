package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.IntroductionPunchCardsModule;
import com.phjt.shangxueyuan.mvp.contract.IntroductionPunchCardsContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.IntroductionPunchCardsActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/18 10:20
 */

@ActivityScope
@Component(modules = IntroductionPunchCardsModule.class, dependencies = AppComponent.class)
public interface IntroductionPunchCardsComponent {
    void inject(IntroductionPunchCardsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        IntroductionPunchCardsComponent.Builder view(IntroductionPunchCardsContract.View view);

        IntroductionPunchCardsComponent.Builder appComponent(AppComponent appComponent);

        IntroductionPunchCardsComponent build();
    }
}