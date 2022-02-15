package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.JournalContract;
import com.phjt.shangxueyuan.mvp.model.JournalModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/18 14:49
 */

@Module
public abstract class JournalModule {

    @Binds
    abstract JournalContract.Model bindJournalModel(JournalModel model);
}