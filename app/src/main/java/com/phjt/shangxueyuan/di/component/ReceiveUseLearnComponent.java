package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.ReceiveUseLearnModule;
import com.phjt.shangxueyuan.mvp.contract.ReceiveUseLearnContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.ReceiveUseLearnFragment;


/**
 * @author: Created by GengYan
 * date: 06/22/2021 10:38
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = ReceiveUseLearnModule.class, dependencies = AppComponent.class)
public interface ReceiveUseLearnComponent {
    void inject(ReceiveUseLearnFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ReceiveUseLearnComponent.Builder view(ReceiveUseLearnContract.View view);

        ReceiveUseLearnComponent.Builder appComponent(AppComponent appComponent);

        ReceiveUseLearnComponent build();
    }
}