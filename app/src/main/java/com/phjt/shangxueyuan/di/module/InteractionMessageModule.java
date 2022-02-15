package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.InteractionMessageContract;
import com.phjt.shangxueyuan.mvp.model.InteractionMessageModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/20 13:54
 */

@Module
public abstract class InteractionMessageModule {

    @Binds
    abstract InteractionMessageContract.Model bindInteractionMessageModel(InteractionMessageModel model);
}