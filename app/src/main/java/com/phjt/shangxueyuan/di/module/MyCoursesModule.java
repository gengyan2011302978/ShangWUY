package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.MyCoursesContract;
import com.phjt.shangxueyuan.mvp.model.MyCoursesModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:00
 */

@Module
public abstract class MyCoursesModule {

    @Binds
    abstract MyCoursesContract.Model bindMyCoursesModel(MyCoursesModel model);
}