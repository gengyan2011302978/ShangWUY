package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.RankingListsModule;
import com.phjt.shangxueyuan.mvp.contract.RankingListsContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.RankingListsActivity;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 15:19
 */

@ActivityScope
@Component(modules = RankingListsModule.class, dependencies = AppComponent.class)
public interface RankingListsComponent {
    void inject(RankingListsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RankingListsComponent.Builder view(RankingListsContract.View view);

        RankingListsComponent.Builder appComponent(AppComponent appComponent);

        RankingListsComponent build();
    }
}