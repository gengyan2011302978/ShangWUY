package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.IncomeRecordModule;
import com.phjt.shangxueyuan.mvp.contract.IncomeRecordContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.IncomeRecordFragment;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 10:56
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = IncomeRecordModule.class, dependencies = AppComponent.class)
public interface IncomeRecordComponent {
    void inject(IncomeRecordFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        IncomeRecordComponent.Builder view(IncomeRecordContract.View view);

        IncomeRecordComponent.Builder appComponent(AppComponent appComponent);

        IncomeRecordComponent build();
    }
}