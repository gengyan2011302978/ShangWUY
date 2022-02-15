package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.VideoDownloadListModule;
import com.phjt.shangxueyuan.mvp.contract.VideoDownloadListContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.VideoDownloadListActivity;


/**
 * @author: Created by Template
 * date: 06/04/2020 15:01
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = VideoDownloadListModule.class, dependencies = AppComponent.class)
public interface VideoDownloadListComponent {
    void inject(VideoDownloadListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        VideoDownloadListComponent.Builder view(VideoDownloadListContract.View view);

        VideoDownloadListComponent.Builder appComponent(AppComponent appComponent);

        VideoDownloadListComponent build();
    }
}