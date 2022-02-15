package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.VipExchangeModule;
import com.phjt.shangxueyuan.mvp.contract.VipExchangeContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.VipExchangeFragment;


/**
 * @author: Created by GengYan
 * date: 08/06/2020 09:51
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = VipExchangeModule.class, dependencies = AppComponent.class)
public interface VipExchangeComponent {
    void inject(VipExchangeFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        VipExchangeComponent.Builder view(VipExchangeContract.View view);

        VipExchangeComponent.Builder appComponent(AppComponent appComponent);

        VipExchangeComponent build();
    }
}