package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.CourseDetailContract;
import com.phjt.shangxueyuan.mvp.model.CourseDetailModel;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 14:34
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class CourseDetailModule {

    @Binds
    abstract CourseDetailContract.Model bindCourseDetailModel(CourseDetailModel model);
}