package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.DocDownloadModule;
import com.phjt.shangxueyuan.mvp.contract.DocDownloadContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.DocDownloadFragment;


/**
 * @author: Created by Template
 * date: 06/03/2020 17:46
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = DocDownloadModule.class, dependencies = AppComponent.class)
public interface DocDownloadComponent {
    void inject(DocDownloadFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DocDownloadComponent.Builder view(DocDownloadContract.View view);

        DocDownloadComponent.Builder appComponent(AppComponent appComponent);

        DocDownloadComponent build();
    }
}