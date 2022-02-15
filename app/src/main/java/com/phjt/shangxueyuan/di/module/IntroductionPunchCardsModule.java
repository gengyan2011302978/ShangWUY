package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.IntroductionPunchCardsContract;
import com.phjt.shangxueyuan.mvp.model.IntroductionPunchCardsModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/18 10:20
 */

@Module
public abstract class IntroductionPunchCardsModule {

    @Binds
    abstract IntroductionPunchCardsContract.Model bindIntroductionPunchCardsModel(IntroductionPunchCardsModel model);
}