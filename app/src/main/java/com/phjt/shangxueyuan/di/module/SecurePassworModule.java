package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.SecurePassworContract;
import com.phjt.shangxueyuan.mvp.model.SecurePassworModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/05/12 11:18
 */

@Module
public abstract class SecurePassworModule {

    @Binds
    abstract SecurePassworContract.Model bindSecurePassworModel(SecurePassworModel model);
}