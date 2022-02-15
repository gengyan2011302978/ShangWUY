package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.ChangeSecurityPasswordContract;
import com.phjt.shangxueyuan.mvp.model.ChangeSecurityPasswordModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/05/12 09:54
 */

@Module
public abstract class ChangeSecurityPasswordModule {

    @Binds
    abstract ChangeSecurityPasswordContract.Model bindChangeSecurityPasswordModel(ChangeSecurityPasswordModel model);
}