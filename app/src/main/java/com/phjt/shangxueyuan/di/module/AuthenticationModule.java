package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.AuthenticationContract;
import com.phjt.shangxueyuan.mvp.model.AuthenticationModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/05/11 15:02
 */

@Module
public abstract class AuthenticationModule {

    @Binds
    abstract AuthenticationContract.Model bindAuthenticationModel(AuthenticationModel model);
}