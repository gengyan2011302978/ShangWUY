package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.DataFragmentModule;
import com.phjt.shangxueyuan.mvp.contract.DataFragmentContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.DataFragment;


/**
 * @author: Created by GengYan
 * date: 06/05/2020 18:12
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = DataFragmentModule.class, dependencies = AppComponent.class)
public interface DataFragmentComponent {
    void inject(DataFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DataFragmentComponent.Builder view(DataFragmentContract.View view);

        DataFragmentComponent.Builder appComponent(AppComponent appComponent);

        DataFragmentComponent build();
    }
}