package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.VideoDownloadListContract;
import com.phjt.shangxueyuan.mvp.model.VideoDownloadListModel;


/**
 * @author: Created by Template
 * date: 06/04/2020 15:01
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class VideoDownloadListModule {

    @Binds
    abstract VideoDownloadListContract.Model bindVideoDownloadListModel(VideoDownloadListModel model);
}