package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.MyWalletModule;
import com.phjt.shangxueyuan.mvp.contract.MyWalletContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWalletActivity;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 09:43
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = MyWalletModule.class, dependencies = AppComponent.class)
public interface MyWalletComponent {
    void inject(MyWalletActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyWalletComponent.Builder view(MyWalletContract.View view);

        MyWalletComponent.Builder appComponent(AppComponent appComponent);

        MyWalletComponent build();
    }
}