package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.ApplyWithdrawalModule;
import com.phjt.shangxueyuan.mvp.contract.ApplyWithdrawalContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.ApplyWithdrawalActivity;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 11:37
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = ApplyWithdrawalModule.class, dependencies = AppComponent.class)
public interface ApplyWithdrawalComponent {
    void inject(ApplyWithdrawalActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ApplyWithdrawalComponent.Builder view(ApplyWithdrawalContract.View view);

        ApplyWithdrawalComponent.Builder appComponent(AppComponent appComponent);

        ApplyWithdrawalComponent build();
    }
}