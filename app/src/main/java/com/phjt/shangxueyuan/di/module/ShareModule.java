package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.ShareContract;
import com.phjt.shangxueyuan.mvp.model.ShareModel;


/**
 * @author: Created by GengYan
 * date: 07/07/2020 17:32
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class ShareModule {

    @Binds
    abstract ShareContract.Model bindShareModel(ShareModel model);
}