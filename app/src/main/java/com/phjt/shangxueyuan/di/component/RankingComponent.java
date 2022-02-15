package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.RankingModule;
import com.phjt.shangxueyuan.mvp.contract.RankingContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.RankingActivity;


/**
 * @author: Created by GengYan
 * date: 01/29/2021 13:57
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = RankingModule.class, dependencies = AppComponent.class)
public interface RankingComponent {
    void inject(RankingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RankingComponent.Builder view(RankingContract.View view);

        RankingComponent.Builder appComponent(AppComponent appComponent);

        RankingComponent build();
    }
}