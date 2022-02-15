package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.PublishedDiaryContract;
import com.phjt.shangxueyuan.mvp.model.PublishedDiaryModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:21
 */

@Module
public abstract class PublishedDiaryModule {

    @Binds
    abstract PublishedDiaryContract.Model bindPublishedDiaryModel(PublishedDiaryModel model);
}