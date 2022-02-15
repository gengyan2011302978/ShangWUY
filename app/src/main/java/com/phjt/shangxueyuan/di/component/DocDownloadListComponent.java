package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.DocDownloadListModule;
import com.phjt.shangxueyuan.mvp.contract.DocDownloadListContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.DocDownloadListActivity;


/**
 * @author: Created by Template
 * date: 06/09/2020 17:37
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = DocDownloadListModule.class, dependencies = AppComponent.class)
public interface DocDownloadListComponent {
    void inject(DocDownloadListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DocDownloadListComponent.Builder view(DocDownloadListContract.View view);

        DocDownloadListComponent.Builder appComponent(AppComponent appComponent);

        DocDownloadListComponent build();
    }
}