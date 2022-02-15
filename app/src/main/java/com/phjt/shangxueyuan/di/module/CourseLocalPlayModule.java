package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.CourseLocalPlayContract;
import com.phjt.shangxueyuan.mvp.model.CourseLocalPlayModel;


/**
 * @author: Created by GengYan
 * date: 09/07/2020 09:20
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class CourseLocalPlayModule {

    @Binds
    abstract CourseLocalPlayContract.Model bindCourseLocalPlayModel(CourseLocalPlayModel model);
}