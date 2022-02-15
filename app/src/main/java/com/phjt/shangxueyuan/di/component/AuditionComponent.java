package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.AuditionModule;
import com.phjt.shangxueyuan.mvp.contract.AuditionContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.AuditionFragment;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 10:49
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = AuditionModule.class, dependencies = AppComponent.class)
public interface AuditionComponent {
    void inject(AuditionFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AuditionComponent.Builder view(AuditionContract.View view);

        AuditionComponent.Builder appComponent(AppComponent appComponent);

        AuditionComponent build();
    }
}