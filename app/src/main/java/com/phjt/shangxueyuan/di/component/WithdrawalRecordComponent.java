package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.WithdrawalRecordModule;
import com.phjt.shangxueyuan.mvp.contract.WithdrawalRecordContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.WithdrawalRecordFragment;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 11:22
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = WithdrawalRecordModule.class, dependencies = AppComponent.class)
public interface WithdrawalRecordComponent {
    void inject(WithdrawalRecordFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WithdrawalRecordComponent.Builder view(WithdrawalRecordContract.View view);

        WithdrawalRecordComponent.Builder appComponent(AppComponent appComponent);

        WithdrawalRecordComponent build();
    }
}