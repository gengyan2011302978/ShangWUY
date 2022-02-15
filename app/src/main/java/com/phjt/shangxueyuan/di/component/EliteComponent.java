package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.EliteModule;
import com.phjt.shangxueyuan.mvp.contract.EliteContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.EliteFragment;


/**
 * @author: Created by GengYan
 * date: 04/02/2020 18:58
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = EliteModule.class, dependencies = AppComponent.class)
public interface EliteComponent {
    void inject(EliteFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EliteComponent.Builder view(EliteContract.View view);

        EliteComponent.Builder appComponent(AppComponent appComponent);

        EliteComponent build();
    }
}