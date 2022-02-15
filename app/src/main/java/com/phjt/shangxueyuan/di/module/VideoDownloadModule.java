package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.VideoDownloadContract;
import com.phjt.shangxueyuan.mvp.model.VideoDownloadModel;


/**
 * @author: Created by Template
 * date: 06/03/2020 17:45
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class VideoDownloadModule {

    @Binds
    abstract VideoDownloadContract.Model bindVideoDownloadModel(VideoDownloadModel model);
}