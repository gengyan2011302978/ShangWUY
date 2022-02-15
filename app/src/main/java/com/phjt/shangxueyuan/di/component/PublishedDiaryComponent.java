package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.PublishedDiaryModule;
import com.phjt.shangxueyuan.mvp.contract.PublishedDiaryContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.PublishedDiaryFragment;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:21
 */

@FragmentScope
@Component(modules = PublishedDiaryModule.class, dependencies = AppComponent.class)
public interface PublishedDiaryComponent {
    void inject(PublishedDiaryFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PublishedDiaryComponent.Builder view(PublishedDiaryContract.View view);

        PublishedDiaryComponent.Builder appComponent(AppComponent appComponent);

        PublishedDiaryComponent build();
    }
}