package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.WithdrawalDetailModule;
import com.phjt.shangxueyuan.mvp.contract.WithdrawalDetailContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.WithdrawalDetailActivity;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 17:05
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = WithdrawalDetailModule.class, dependencies = AppComponent.class)
public interface WithdrawalDetailComponent {
    void inject(WithdrawalDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WithdrawalDetailComponent.Builder view(WithdrawalDetailContract.View view);

        WithdrawalDetailComponent.Builder appComponent(AppComponent appComponent);

        WithdrawalDetailComponent build();
    }
}