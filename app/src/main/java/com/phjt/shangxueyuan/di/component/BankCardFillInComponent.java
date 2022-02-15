package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.BankCardFillInModule;
import com.phjt.shangxueyuan.mvp.contract.BankCardFillInContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.BankCardFillInActivity;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 15:15
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = BankCardFillInModule.class, dependencies = AppComponent.class)
public interface BankCardFillInComponent {
    void inject(BankCardFillInActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BankCardFillInComponent.Builder view(BankCardFillInContract.View view);

        BankCardFillInComponent.Builder appComponent(AppComponent appComponent);

        BankCardFillInComponent build();
    }
}