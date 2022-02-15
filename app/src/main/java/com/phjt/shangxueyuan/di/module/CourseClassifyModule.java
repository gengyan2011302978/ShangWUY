package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.CourseClassifyContract;
import com.phjt.shangxueyuan.mvp.model.CourseClassifyModel;


/**
 * @author: Created by GengYan
 * date: 05/06/2020 17:36
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class CourseClassifyModule {

    @Binds
    abstract CourseClassifyContract.Model bindCourseClassifyModel(CourseClassifyModel model);
}