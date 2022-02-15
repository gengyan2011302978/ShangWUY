package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.LiveSharingContract;
import com.phjt.shangxueyuan.mvp.model.LiveSharingModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/04/07 17:27
 */

@Module
public abstract class LiveSharingModule {

    @Binds
    abstract LiveSharingContract.Model bindLiveSharingModel(LiveSharingModel model);
}