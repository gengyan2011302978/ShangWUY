package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.LearnRankModule;
import com.phjt.shangxueyuan.mvp.contract.LearnRankContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.LearnRankActivity;


/**
 * @author: Created by GengYan
 * date: 06/22/2021 11:23
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = LearnRankModule.class, dependencies = AppComponent.class)
public interface LearnRankComponent {
    void inject(LearnRankActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        LearnRankComponent.Builder view(LearnRankContract.View view);

        LearnRankComponent.Builder appComponent(AppComponent appComponent);

        LearnRankComponent build();
    }
}