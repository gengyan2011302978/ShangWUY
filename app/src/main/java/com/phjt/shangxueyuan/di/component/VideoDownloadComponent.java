package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.VideoDownloadModule;
import com.phjt.shangxueyuan.mvp.contract.VideoDownloadContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.VideoDownloadFragment;


/**
 * @author: Created by Template
 * date: 06/03/2020 17:45
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = VideoDownloadModule.class, dependencies = AppComponent.class)
public interface VideoDownloadComponent {
    void inject(VideoDownloadFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        VideoDownloadComponent.Builder view(VideoDownloadContract.View view);

        VideoDownloadComponent.Builder appComponent(AppComponent appComponent);

        VideoDownloadComponent build();
    }
}