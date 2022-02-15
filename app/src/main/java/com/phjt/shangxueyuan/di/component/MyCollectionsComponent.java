package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.MyCollectionsModule;
import com.phjt.shangxueyuan.mvp.contract.MyCollectionsContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.MyCollectionsFragment;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/08/26 09:53
 */

@FragmentScope
@Component(modules = MyCollectionsModule.class, dependencies = AppComponent.class)
public interface MyCollectionsComponent {
    void inject(MyCollectionsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyCollectionsComponent.Builder view(MyCollectionsContract.View view);

        MyCollectionsComponent.Builder appComponent(AppComponent appComponent);

        MyCollectionsComponent build();
    }
}