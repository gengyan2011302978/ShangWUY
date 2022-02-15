package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.DocDownloadListContract;
import com.phjt.shangxueyuan.mvp.model.DocDownloadListModel;


/**
 * @author: Created by Template
 * date: 06/09/2020 17:37
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class DocDownloadListModule {

    @Binds
    abstract DocDownloadListContract.Model bindDocDownloadListModel(DocDownloadListModel model);
}