package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.DiscipleGroupContract;
import com.phjt.shangxueyuan.mvp.model.DiscipleGroupModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/03 17:45
 */

@Module
public abstract class DiscipleGroupModule {

    @Binds
    abstract DiscipleGroupContract.Model bindDiscipleGroupModel(DiscipleGroupModel model);
}