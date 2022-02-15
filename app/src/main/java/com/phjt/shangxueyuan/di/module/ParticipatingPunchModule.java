package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.ParticipatingPunchContract;
import com.phjt.shangxueyuan.mvp.model.ParticipatingPunchModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:21
 */

@Module
public abstract class ParticipatingPunchModule {

    @Binds
    abstract ParticipatingPunchContract.Model bindParticipatingPunchModel(ParticipatingPunchModel model);
}