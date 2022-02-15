package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.PointsDetailsModule;
import com.phjt.shangxueyuan.mvp.contract.PointsDetailsContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.PointsDetailsActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 15:09
 */

@ActivityScope
@Component(modules = PointsDetailsModule.class, dependencies = AppComponent.class)
public interface PointsDetailsComponent {
    void inject(PointsDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PointsDetailsComponent.Builder view(PointsDetailsContract.View view);

        PointsDetailsComponent.Builder appComponent(AppComponent appComponent);

        PointsDetailsComponent build();
    }
}